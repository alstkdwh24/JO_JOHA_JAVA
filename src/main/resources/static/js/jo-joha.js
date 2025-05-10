document.addEventListener("DOMContentLoaded", function () {
    // 카카오 SDK 초기화
    Kakao.init("b11d1aa7abe401182ff9c1efce7257ec"); // 실제 JavaScript 키로 교체
    console.log(Kakao.isInitialized()); // SDK 초기화 확인 (true 출력)

    let joJoha = document.querySelector(".kakao-login-btn");

    joJoha.addEventListener("click", function () {
        loginWithKakao();
    });
});

function loginWithKakao() {
    Kakao.Auth.authorize({
        redirectUri: "http://localhost:8081/mains" // 설정한 Redirect URI
    });
}