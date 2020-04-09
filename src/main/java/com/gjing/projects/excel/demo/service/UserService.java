package com.gjing.projects.excel.demo.service;

import com.gjing.projects.excel.demo.entity.User;
import com.gjing.projects.excel.demo.enums.Gender;
import com.gjing.projects.excel.demo.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gjing
 **/
@Service
public class UserService {
    @Resource
    private UserRepository userRepository;

    /**
     * 添加模拟数据
     *
     * @param number 数据条数
     */
    public void saveUsers(int number) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            userList.add(new User("用户" + i, i * 2, i % 2 == 0 ? Gender.MAN : Gender.WO_MAN));
        }
        this.userRepository.saveAll(userList);
    }

    /**
     * 查询所有数据
     *
     * @return List<User>
     */
    public List<User> userList() {
        return this.userRepository.findAll();
    }

    /**
     * 分页查询数据
     *
     * @param page 页数
     * @return List<User>
     */
    public List<User> userListPage(int page) {
        return this.userRepository.findAll(PageRequest.of(page - 1, 5)).getContent();
    }

    /**
     * 添加所有数据
     * @param userList userList
     */
    public void saveUsers(List<User> userList) {
        this.userRepository.saveAll(userList);
    }
}
