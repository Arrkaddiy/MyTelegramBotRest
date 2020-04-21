package ru.league.telegram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.league.telegram.entity.User;
import ru.league.telegram.repo.UserRepositories;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepositories userRepositories;

    public User getUserByChatId(Long chatId) {
        log.debug("Поиск пользователя по Chat Id - '{}'", chatId);
        return userRepositories.findByChatId(chatId);
    }

    public User create(User user) {
        log.debug("Создание пользователя - '{}'", user);
        return userRepositories.save(user);
    }

    public User update(User user) {
        log.debug("Обновление пользователя - '{}'", user);
        return userRepositories.save(user);
    }

    public void delete(User user) {
        log.debug("Удаление пользователя - '{}'", user);
        userRepositories.delete(user);
    }
}
