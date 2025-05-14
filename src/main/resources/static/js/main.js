document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const authorizationCode = urlParams.get("code");
    console.log("authorizationCode"+authorizationCode);
    console.log(urlParams);
    let accessToken = "";
    if (authorizationCode) {
        console.log("리디렉션에서 받은 인증 코드:", authorizationCode);

        $.ajax({
            type: "POST", // 'Get'을 'POST'로 변경
            url: "/api/kakao/token",
            headers: {
                "Content-Type": "application/json" // JSON 요청
            },
            data: JSON.stringify({
                authorizationCodes: authorizationCode // Body에 데이터 전달
            }),
            success: function (response) {
                const accessToken = response.access_token;
                const refreshToken = response.refresh_token;
                const idToken = response.id_token;

                console.log("Access Token:", accessToken);
                console.log("Refresh Token:", refreshToken);
                console.log("ID Token:", idToken);


            },
            error: function (xhr, status, error) {
                console.error("Error:", error);
                console.error("Status:", status);
                console.error("Response:", xhr.responseText);
            }
        });

    }
})