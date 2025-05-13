document.addEventListener("DOMContentLoaded", function () {
    // 카카오 SDK 초기화
    Kakao.init("b11d1aa7abe401182ff9c1efce7257ec"); // 실제 JavaScript 키로 교체
    console.log("Kakao SDK 초기화:", Kakao.isInitialized()); // true 출력
    const url = "http://localhost:8081";

    let joJoha = document.querySelector(".kakao-login-btn"); // 클래스 확인
    if (joJoha) {

        // 팝업 방식
        joJoha.addEventListener("click", function () {
            console.log("카카오 로그인 버튼 클릭");

            Kakao.Auth.login({

                success: function (response) {
                    console.log("카카오 로그인 성공:", response);


                    if (window.opener) {
                        window.opener.postMessage(
                            {accessToken: response.access_token},
                            "http://localhost:8081"
                            );
                        window.close();
                    } else {
                        // 팝업 방식이 아니면 메인으로 리다이렉트
                        location.href = "http://localhost:8081/main";
                    }

                },
                fail: function (err) {
                    console.error("로그인 실패:", err);

                    if (window.opener) {
                        window.close();
                    }
                }
            })
        });
    } else {
        console.error("'.kakao-login-btn'를 찾을 수 없습니다.");
    }

    // 리다이렉트 방식
    let redirectLogin = document.querySelector(".btn_g highlight submit");
    redirectLogin.onclick = function () {
        Kakao.Auth.authorize({
            redirectUri: "http://localhost:8081/main"
        });
    };

    // 메시지 수신
    window.addEventListener("message", function (event) {
        console.log("Received message from:", event.origin);

        if (event.origin === `http://localhost:8081`) {
            console.log("카카오 로그인 성공:", event.data);
            const accessToken = event.data.accessToken;
            console.log("Access Token:", accessToken);

            fetch("/kakao/auth", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({accessToken: accessToken}),
            })
                .then((response) => response.json())
                .then((data) => {
                    console.log("서버 응답ㄴ:", accessToken);

                })
                .catch((error) => {
                    console.error("에러 발생:", error);
                });
        } else {
            console.error("Unexpected origin:", event.origin);
        }
    });
});