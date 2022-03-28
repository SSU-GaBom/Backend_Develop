package com.example.bom.gabom.service;

import com.example.bom.gabom.model.dto.FindUserDto;
import com.example.bom.gabom.model.entity.User;
import com.example.bom.gabom.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class FindUserService {

    private final int FIND_ID = 1;
    private final int FIND_PW = 2;

    private final UserRepository userRepository;
    private final MailAuthService mailAuthService;

    public User findId(FindUserDto findUserDto, HttpSession session){
        String[] info = new String[]{findUserDto.getEmail(), findUserDto.getUserName()};

        User user = userRepository.findByEmailAndUserName(info[0], info[1]);

        mailAuthService.mailAuth(user, info, FIND_ID, session);

        //여기는 난수 생성 후 비교까지는 하지 않은 단계임. 이후에 난수랑 비교해서 같으면 그 때 아이디를 넘겨줘야함.
        return user;
    }

    public User findPw(FindUserDto findUserDto, HttpSession session){
        String[] info = new String[]{findUserDto.getEmail(), findUserDto.getUserName(), findUserDto.getUserId()};

        User user = userRepository.findByEmailAndUserNameAndUserId(info[0], info[1], info[2]);

        mailAuthService.mailAuth(user, info, FIND_PW, session);

        return user;
    }
}