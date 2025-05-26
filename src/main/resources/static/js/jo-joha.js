document.addEventListener("DOMContentLoaded", function () {
    let kakaoRestApiKey = "e86f19da617db502540cbc18b5aa5cbf";
    let RedirectUri = "http://localhost:8081/main";
    const NaverRedirectUri = "http://localhost:8081/naver-callback";

    // 카카오 SDK 초기화
    Kakao.init("b11d1aa7abe401182ff9c1efce7257ec"); // 실제 JavaScript 키로 교체
    console.log("Kakao SDK 초기화:", Kakao.isInitialized()); // true 출력
    const url = "http://localhost:8081";
    let accessToken = null;

    let joJoha = document.querySelector(".kakao-login-btn"); // 클래스 확인
    joJoha.onclick = function () {

        Kakao.Auth.authorize({
            // 인증 완료 리디렉션 URI 설정
            redirectUri: RedirectUri // 카카오 개발자 콘솔에 등록된 리디렉션 URI


        })


    }


    let naver_id_login = new naver.LoginWithNaverId({
        clientId: "ApscJ4M6B8itF7sgJsCI", // 환경 변수로 관리
        callbackUrl: "http://localhost:8081/main", // 환경 변수로 관리
        isPopup: true, // 팝업 방식 사용 여부
        loginButton: {
            class: "naver-login-btn"
            , color: "green", type: 3, height: 48
        }, // 로그인 버튼 스타일
    });

    // 스크립트 로드 완료 이후 init() 호출
    window.onload = function () {
        naver_id_login.init();
    };

    // 네이버 로그인 버튼 클릭 이벤트 추가
    let naverLoginBtn = document.querySelector(".naver-login-btn");
    if (naverLoginBtn) {
        naverLoginBtn.addEventListener("click", function () {
            console.log("네이버 로그인 버튼 클릭됨.");
            try {
                naver_id_login.authorize(); // 네이버 로그인 실행
            } catch (err) {
                console.error("네이버 로그인 중 오류 발생:", err);
                alert("네이버 로그인에 실패했습니다. 잠시 후 다시 시도해주세요.");
            }
        });
    } else {
        console.error('네이버 로그인 버튼을 찾을 수 없습니다. "naver-login-btn" 클래스를 확인하세요.');
        alert("로그인 버튼 UI를 찾을 수 없습니다. 관리자에게 문의하세요.");
    }

    let gsi_material_button = document.querySelector(".gsi-material-button");
    gsi_material_button.addEventListener("click", function () {
        window.location.href = "/api/kakao/google/auth";


    });

//JO-JOHA회원가입 모달창
    let joJohaJoin = document.getElementById("jo-joha-join");
    let joJohaJoinModalFragment = document.getElementById("jo-joha-join-modal_fragment");
    joJohaJoin.addEventListener("click", function () {
        joJohaJoinModalFragment.style.display = "flex";



    });
    joJohaJoinModalFragment.addEventListener("click", function (e) {
        if(e.target === joJohaJoinModalFragment) {
            joJohaJoinModalFragment.style.display = "none";
        }
    })

    let joJohaLoginBox=document.querySelector(".jo-joha-login-box");
    let realJoJohaLoginModal=document.querySelector(".real-jo-joha-login-modal");
    joJohaLoginBox.addEventListener("click",function(){
        realJoJohaLoginModal.style.display="flex";
    });
    realJoJohaLoginModal.addEventListener("click",function(e){
        if(e.target===realJoJohaLoginModal){
            realJoJohaLoginModal.style.display="none";
        }
    })
    let jo_joha_join_button=document.querySelector(".jo-joha-join-button");


    jo_joha_join_button.onclick=function() {
        let id = document.getElementById("id").value;
        let pw = document.getElementById("pw").value;
        let nickname = document.getElementById("nickname").value;
        let email = document.getElementById("email").value;
        let name = document.getElementById("name").value;
        let user="USER_ROLE";
        console.log("1"+email);
        console.log("2"+id);
        $.ajax({
            url: "/login/join",
            type: "POST",
            contentType: "application/json", // 반드시 JSON으로 설정
            data: JSON.stringify({
                email: email,
                username: id,
                password: pw,
                nickname: nickname,
                name: name,
                roles: user
                // 동적으로 가져온 이메일 값
            }),
            success: function (response) {
                alert("성공적으로 가입되었습니다!");
            },
            error: function (xhr, status, error) {
                alert("오류가 발생했습니다: " + error);
            }
        });
    }


});

