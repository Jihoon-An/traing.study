package signUp.main.client;

import myCustom.Input;
import signUp.main.model.SignUpDTO;

public class View {

    protected static int mainMenu(){
        System.out.println("<<로그인 회원가입>>");
        System.out.println("1. 로그인");
        System.out.println("2. 회원가입");
//        System.out.println("3. 정보조회");
        System.out.println("0. 종료");
        return Input._integer_(0,2);
    }

    protected static SignUpDTO signInMenu(){
        System.out.println("<<로그인>>");
        System.out.print("아이디 : ");
        String id = Input._string_(10);
        System.out.print("비밀번호 : ");
        String pw = Input._string_(100);
        return new SignUpDTO(id, pw);
    }

    protected static void signInResult(String result){
        if(result.equals("success")){
            System.out.println("로그인에 성공하였습니다.");
        }
        else if(result.equals("fail")){
            System.out.println("로그인에 실패하였습니다.");
        }else if(result.equals("notFoundId")) {
            System.out.println("아이디를 찾을 수 없습니다.");
        }else{
            System.out.println("오류가 발생했습니다.");
        }
    }

    protected static SignUpDTO signUpMenu(){
        System.out.println("<<회원가입>>");
        System.out.print("이름 : ");
        String name = Input._string_(10);
        System.out.print("아이디 : ");
        String id = Input._string_(10);
        System.out.print("비밀번호 : ");
        String pw = Input._string_(100);
        return new SignUpDTO(id, pw, name);
    }

    protected static void signUpResult(String result){
        if(result.equals("success")){
            System.out.println("회원가입에 성공하였습니다.");
        }
        else if(result.equals("fail")){
            System.out.println("회원가입에 실패하였습니다.");
        }
        else{
            System.out.println("오류가 발생하였습니다.");
        }
    }

    protected static void exit(){
        System.out.println("프로그램을 종료합니다.");
    }

    protected static void error(){
        System.out.println("오류가 발생하였습니다.");
    }
}
