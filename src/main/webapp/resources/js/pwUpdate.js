$("#mod_password1").blur(function() {
    var pw = document.getElementById("mod_password1").value;

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

$("#mod_password2").blur(function() {
    var pw = document.getElementById("mod_password1").value;
    var pw2 = document.getElementById("mod_password2").value;

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

function update(userId, event) {

    event.preventDefault();

    let data = $("#pwUpdate").serialize();

    $.ajax({
        type: "put",
        url: `/api/profile/pw-update/${userId}`,
        data: data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json"

    }).done(res=>{
        console.log("성공", res);
        alert("비밀번호가 정상적으로 변경되었습니다.");
        location.href = `/profile/${userId}/update`;

    }).fail(error=>{
        if(error.data == null) {
            alert(error.responseText);
        }else {
            error.responseText;
        }
    });

}