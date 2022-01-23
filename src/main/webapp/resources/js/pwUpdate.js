function update(userId, event) {

    event.preventDefault();

    let data = $("#pwUpdate").serialize();

    $.ajax({
        type: "put",
        url: `/api/profile/pwUpdate/${userId}`,
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