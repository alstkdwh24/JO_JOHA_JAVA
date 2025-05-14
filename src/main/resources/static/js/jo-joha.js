document.addEventListener("DOMContentLoaded", function () {
    let kakaoRestApiKey = "e86f19da617db502540cbc18b5aa5cbf";
    let RedirectUri = "http://localhost:8081/main";

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



    }})

