function update(userId, event) {

    event.preventDefault();

    let data = $("#profileUpdate").serialize();

    $.ajax({
        type: "put",
        url: `/api/profile/update/${userId}`,
        data: data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json"

    }).done(res=>{
        console.log("성공", res);
        location.href = `update`;

    }).fail(error=>{
        console.log(error);
        if(error.data == null) {
            alert(error.responseText);
        }else {
            error.responseText;
        }
    });

}