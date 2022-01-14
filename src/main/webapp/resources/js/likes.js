function likes(writeId, event) {
    event.preventDefault();

    let likesButton = $("#likesButton");
    let likesText = $("#likesText");
    let likesCountStr = Number($("#likesCount").text());

    if(likesButton.hasClass("btn-primary")) {
        $.ajax({
            type: "post",
            url: `/api/board/likes/${writeId}`,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: "json"

        }).done(res=>{
            console.log("성공", res);

            likesButton.removeClass("btn btn-primary btn-sm");
            likesButton.addClass("btn btn-secondary btn-sm");
            likesButton.text("추천 취소");
            $("#likesCount").text(likesCountStr+1);

        }).fail(error=>{
            console.log(error);
            if(error.data == null) {
                alert(error.responseText);
            }else {
                error.responseText;
            }
        });
    }

    else {
        $.ajax({
            type: "delete",
            url: `/api/board/likes/${writeId}`,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: "json"

        }).done(res=>{
            console.log("성공", res);

            likesButton.removeClass("btn btn-secondary btn-sm");
            likesButton.addClass("btn btn-primary btn-sm");
            likesButton.text("추천");
            $("#likesCount").text(likesCountStr-1);

        }).fail(error=>{
            console.log(error);
            if(error.data == null) {
                alert(error.responseText);
            }else {
                error.responseText;
            }
        });
    }


}

