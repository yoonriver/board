function userDelete(userId, pageNum, event) {
    event.preventDefault();

    var result = confirm("정말 삭제하시겠습니까?");

    if(result) {
        $.ajax({
            type: "delete",
            url: `/api/user/delete/${userId}`,
            dataType: "json"

        }).done(res=>{
            console.log("성공", res);
            location.href = `/user/list?page=${pageNum}&keyword=&option=&category=`;

        }).fail(error=>{
            console.log(error);
            if(error.data == null) {
                alert(error.responseText);
            }else {
                error.responseText;
            }
        });

    }else {

    }


}