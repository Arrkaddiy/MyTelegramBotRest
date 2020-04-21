package ru.league.telegram.repo;

import org.springframework.data.repository.CrudRepository;
import ru.league.telegram.entity.User;

public interface UserRepositories extends CrudRepository<User, Long> {

    User findByChatId(long chatId);
}
