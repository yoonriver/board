// 아이디 유효성 검사(true = 중복 / false = 중복x)
$("#username").blur(function() {
    var username = $("#username").val();
    if(username == "") {
        $("#idCheck").text("아이디를 입력해주세요");
        $("#idCheck").css("color", "red");
        $("#regSubmit").attr("disabled", true);
    }

    $.ajax({
        type: "get",
        url: "/api/auth/idCheck/" + username,
        dataType: "json"

    }).done(res => {
        if (res.data == true){
            $("#idCheck").text("사용중인 아이디 입니다.");
            $("#idCheck").css("color", "red");
            $("#regSubmit").attr("disabled", true);
        }else {
            $("#idCheck").text("사용 가능한 아이디 입니다.");
            $("#idCheck").css("color", "blue");
            $("#regSubmit").attr("disabled", false);

            if(username.length > 20 || username.length < 2) {
                $("#idCheck").text("아이디는 2자 이상 20자 이내로 해주세요.");
                $("#idCheck").css("color", "red");
                $("#regSubmit").attr("disabled", true);
            }

        }

    }).fail(error => {
        console.log("오류", error);
    });
});

// 이메일 유효성 검사(true = 중복 / false = 중복x)
$("#userEmail").blur(function() {
    var userEmail = $("#userEmail").val();

    if(userEmail == ""){
        $("#emailCheck").text("이메일을 입력해주세요");
        $("#emailCheck").css("color", "red");
        $("#regSubmit").attr("disabled", true);
    }

    $.ajax({
        type: "get",
        url: "/api/auth/emailCheck/" + userEmail,
        dataType: "json"
    }).done(res => {
        if (res.data == true){
            $("#emailCheck").text("사용중인 이메일 입니다.");
            $("#emailCheck").css("color", "red");
            $("#regSubmit").attr("disabled", true);
        }else {
            $("#emailCheck").text("사용 가능한 이메일 입니다.");
            $("#emailCheck").css("color", "blue");
            $("#regSubmit").attr("disabled", false);
        }

    }).fail(error => {
        console.log("오류", error);
    });
});

// 비밀번호 유효성 검사
$("#password1").blur(function() {
    var pw = document.getElementById("password1").value;

    var pattern1 = /[0-9]/;
    var pattern2 = /[a-zA-Z]/;
    var pattern3 = /[~!@\#$%<>^&*]/;

    if(!pattern1.test(pw)||!pattern2.test(pw)||!pattern3.test(pw)||pw.length<8||pw.length>12) {
        $("#pw1Check").text("영문+숫자+특수기호 8자리 이상으로 구성하여야 합니다.");
        $("#pw1Check").css("color", "red");
        $("#regSubmit").attr("disabled", true);
    }else {
        $("#pw1Check").text("사용 가능한 패스워드 입니다.");
        $("#pw1Check").css("color", "blue");
        $("#regSubmit").attr("disabled", false);
    }

});

$("#password2").blur(function() {
    var pw = document.getElementById("password1").value;
    var pw2 = document.getElementById("password2").value;

    var pattern1 = /[0-9]/;
    var pattern2 = /[a-zA-Z]/;
    var pattern3 = /[~!@\#$%<>^&*]/;

    if(pw != pw2) {
        $("#pw2Check").text("비밀번호와 확인 비밀번호가 다릅니다.");
        $("#pw2Check").css("color", "red");
        $("#regSubmit").attr("disabled", true);
    }else {
        $("#pw2Check").text("비밀번호가 일치 합니다.");
        $("#pw2Check").css("color", "blue");
        $("#regSubmit").attr("disabled", false);
    }

});