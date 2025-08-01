package gianlucafiorani.U5_W3_D5.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import gianlucafiorani.U5_W3_D5.entities.User;
import gianlucafiorani.U5_W3_D5.exceptions.BadRequestException;
import gianlucafiorani.U5_W3_D5.exceptions.NotFoundException;
import gianlucafiorani.U5_W3_D5.payloads.NewUserDTO;
import gianlucafiorani.U5_W3_D5.payloads.RoleUpdate;
import gianlucafiorani.U5_W3_D5.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private Cloudinary imgUploader;


    public User save(NewUserDTO payload) {
        this.usersRepository.findByEmail(payload.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + user.getEmail() + " è già in uso!");
        });
        User newUser = new User(payload.name(), payload.surname(), payload.email(), bcrypt.encode(payload.password()));
        newUser.setAvatarURL("https://ui-avatars.com/api/?name=" + payload.name() + "+" + payload.surname());
        User savedUser = this.usersRepository.save(newUser);
        log.info("L'utente con id: " + savedUser.getId() + " è stato salvato correttamente!");
        return savedUser;
    }

    public Page<User> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        return this.usersRepository.findAll(pageable);
    }

    public User findById(UUID userId) {
        return this.usersRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public User findByIdAndUpdate(UUID userId, NewUserDTO payload) {
        User found = this.findById(userId);
        if (!found.getEmail().equals(payload.email()))
            this.usersRepository.findByEmail(payload.email()).ifPresent(user -> {
                throw new BadRequestException("L'email " + user.getEmail() + " è già in uso!");
            });
        found.setName(payload.name());
        found.setSurname(payload.surname());
        found.setEmail(payload.email());
        found.setPassword(payload.password());
        found.setAvatarURL("https://ui-avatars.com/api/?name=" + payload.name() + "+" + payload.surname());
        User modifiedUser = this.usersRepository.save(found);
        log.info("L'utente con id " + found.getId() + " è stato modificato!");
        return modifiedUser;
    }

    public User updateRole(UUID userId, RoleUpdate payload) {
        User found = this.findById(userId);
        found.setRole(payload.role());
        User modifiedUser = this.usersRepository.save(found);
        log.info("L'utente con id " + found.getId() + " ha cambiato ruolo in " + found.getRole());
        return modifiedUser;
    }

    public void findByIdAndDelete(UUID userId) {
        User found = this.findById(userId);
        this.usersRepository.delete(found);
    }

    public User findByEmail(String email) {
        return this.usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con l'email " + email + " non è stato trovato!"));
    }

    public User uploadAvatar(UUID id, MultipartFile file) {
        try {
            User found = this.findById(id);
            Map result = imgUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageURL = (String) result.get("url");
            found.setAvatarURL(imageURL);
            return usersRepository.save(found);
        } catch (Exception e) {
            throw new BadRequestException("Ci sono stati problemi nel salvataggio del file!");
        }
    }
}