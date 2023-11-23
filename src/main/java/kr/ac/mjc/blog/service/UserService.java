package kr.ac.mjc.blog.service;

import kr.ac.mjc.blog.domain.User;
import kr.ac.mjc.blog.dto.UserRequest;
import kr.ac.mjc.blog.dto.UserResponse;
import kr.ac.mjc.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserResponse join(UserRequest request){
        User checkUser=userRepository.findByEmail(request.getEmail());

        UserResponse response=new UserResponse();
        if(checkUser!=null){    //이미 가입된 이메일 경우
            response.setSuccess(false);
            response.setMessage("이미 가입된 이메일입니다");
            return response;
        }
        //가입된 이메일이 없는경우
        userRepository.save(request.toEntity());
        response.setSuccess(true);
        response.setMessage("회원가입이 완료되었습니다");
        return response;
    }

    public UserResponse login(UserRequest request){
        UserResponse response=new UserResponse();
        User checkUser=userRepository
                .findByEmailAndPassword(request.getEmail(),request.getPassword());
        //아이디 또는 패스워드가 틀렸을경우
        if(checkUser==null){
            response.setSuccess(false);
            response.setMessage("아이디 또는 패스워드가 틀렸습니다");
            return response;
        }
        //아이디와 패스워드가 맞았을경우
        response.setSuccess(true);
        response.setMessage("로그인 성공");
        response.setUser(checkUser);
        return response;
    }

}
