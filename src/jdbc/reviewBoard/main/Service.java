package jdbc.reviewBoard.main;

import jdbc.reviewBoard.dao.ReviewBoardDAO;
import jdbc.reviewBoard.dto.Post;
import myCustom.Input;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Service {
    private static String dbID = "review_board";
    private static String dbPW = "review";

    public static void service() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }

        while (true) {
            System.out.println("\n<< 카페 메뉴 관리 프로그램 >>");
            System.out.println("1. 글 작성");
            System.out.println("2. 글 목록 출력");
            System.out.println("3. 글 내용 보기 (글 번호)");
            System.out.println("4. 글 삭제 (글 번호)");
            System.out.println("5. 글 수정 (글 번호)");
            System.out.println("6. 글 검색 (작성자 or 글 제목)");
            System.out.println("0. 프로그램 종료");
            int menu = Input._integer_(0, 6);

            if (menu == 1) { // 글 작성
                ReviewBoardDAO boardDAO = new ReviewBoardDAO(dbID, dbPW);

                System.out.println("작성자의 이름을 입력하세요");
                String poster = Input._string_();
                System.out.println("작성하실 글의 제목을 입력하세요");
                String postTitle = Input._string_();
                System.out.println("내용을 입력하세요");
                String postCont = Input._string_();
                int result = boardDAO.insert(new Post(poster, postTitle, postCont));

                if (result > 0) {
                    System.out.println("작성 성공");
                } else {
                    System.out.println("작성 실패");
                }
            } else if (menu == 2) { // 글 목록 출력
                outPostList();

            } else if (menu == 3) { // 글 읽기
                System.out.println("읽을 게시글의 POST_NO을 입력하세요.");
                int findPostNo = Input._integer_();
                outPost(findPostNo);

            } else if (menu == 4) { // 글 지우기
                outPostList();
                ReviewBoardDAO boardDAO = new ReviewBoardDAO(dbID, dbPW);
                // 삭제 postNo 입력
                System.out.println("----삭제할 글의 글 번호 입력----");
                int delPostNo = Input._integer_();

                //확인
                outPost(delPostNo);
                System.out.println("위 항목을 지우시겠습니까?(Y,N)");
                String confirm = Input._string_("Y", "y", "N", "n");
                if (confirm.equals("y") || confirm.equals("Y")) {
                    // sql 입력
                    int result = boardDAO.delete(delPostNo);

                    if (result > 0) {
                        System.out.println("삭제 성공!!");
                    } else {
                        System.out.println("삭제 대상이 없습니다.");
                    }
                } else {
                    System.out.println("메인 메뉴로 돌아갑니다.");
                }
            } else if (menu == 5) { // 글 수정하기
                ReviewBoardDAO boardDAO = new ReviewBoardDAO(dbID, dbPW);
                outPostList();
                // 수정 ID 입력
                System.out.print("수정할 메뉴 ID 입력.");
                int rePostNo = Input._integer_();
                // 수정할 컬럼 내용 출력
                outPost(rePostNo);

                // 수정 컬럼 입력
                System.out.println("수정할 컬럼");
                System.out.println("작성자 : Poster");
                System.out.println("제목 : Post_title");
                System.out.println("내용 : Post_cont");
                String rePostCol = Input._string_(
                        "POSTER", "POST_TITLE", "POST_CONT","내용",
                        "poster", "post_title", "post_cont","제목",
                        "Poster", "Post_title", "Post_cont","작성자");

                // 수정 내용 입력

                String rePostCon = "";

                Post rePost = boardDAO.select(rePostNo);

                if (rePostCol.equals("POST_CONT") || rePostCol.equals("post_cont") || rePostCol.equals("Post_cont")||rePostCol.equals("내용")) {
                    System.out.println("수정 내용 입력(최대 500글자)");
                    rePostCon = Input._string_(500);
                    rePost.setPostCont(rePostCon);
                } else if (rePostCol.equals("POST_TITLE") || rePostCol.equals("post_title") || rePostCol.equals("Post_title")||rePostCol.equals("제목")) {
                    System.out.println("수정 제목 입력(최대 30글자)");
                    rePostCon = Input._string_(30);
                    rePost.setPostTitle(rePostCon);
                } else {
                    System.out.println("수정 작성자 입력(최대 10글자)");
                    rePostCon = Input._string_(10);
                    rePost.setPoster(rePostCon);
                }

                // sql 입력
                int result = 0;
                try {
                    result = boardDAO.update(rePost);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("같은 오류가 반복되면 관리자에게 문의하세요.");
                }
                if (result > 0) {
                    System.out.println("수정 성공!!");
                } else {
                    System.out.println("수정 대상이 없습니다.");
                }
            } else if (menu == 6) {
                ReviewBoardDAO reviewBoardDAO = new ReviewBoardDAO(dbID, dbPW);

                System.out.println("Search 대상을 입력하세요.(작성자와 게시글 제목에서 검색합니다.)");
                String searchText = Input._string_(30);
                ArrayList<Integer> postNoArr = reviewBoardDAO.search(searchText);

                if(postNoArr.size() != 0) {
                    System.out.println("글 번호 \t: 작성자 \t: 글 제목 \t: 작성 시간");
                    for (int findPostNo : postNoArr) {
                        Post result = reviewBoardDAO.select(findPostNo);
                        System.out.println(result.getPostNo()+ " \t: " + result.getPoster()+ " \t: " +result.getPostTitle()+ "  \t: " +result.getPostTime());
                    }
                }
                else{
                    System.out.println("검색 대상이 없습니다.");
                }

            } else {
                System.out.println("프로그램을 종료합니다.");
                System.exit(0);
            }
        }
    }

    static void outPostList() {
        ReviewBoardDAO boardDAO = new ReviewBoardDAO(dbID, dbPW);
        ArrayList<Post> resultArr = boardDAO.select();
        if (resultArr.size() == 0) {
            System.out.println("글 목록이 존재하지 않습니다.");
        } else {
            System.out.println("글 번호 \t: 작성자 \t: 글 제목 \t: 작성 시간");
            for (Post result : resultArr) {
                System.out.println(result.getPostNo()+ " \t: " + result.getPoster()+ " \t: " +result.getPostTitle()+ "  \t: " +result.getPostTime());
            }
        }
    }

    static void outPost(int findPostNo) {
        ReviewBoardDAO boardDAO = new ReviewBoardDAO(dbID, dbPW);
        Post result = boardDAO.select(findPostNo);
        if (result == null) {
            System.out.println("글 번호를 잘못 입력하셨습니다.");
        } else {
            System.out.println("글 번호 \t: 작성자 \t: 글 제목 \t: 작성 시간");
            System.out.println(result.getPostNo()+ " \t: " + result.getPoster()+ " \t: " +result.getPostTitle()+ "  \t: " +result.getPostTime());
            System.out.println("vv 글 내용 vv");
            System.out.println(result.getPostCont());
        }
    }
}


