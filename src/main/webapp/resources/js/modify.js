function writesModify(writeId, event) {

    event.preventDefault();

    let data = $("#writesModify").serialize();


    $.ajax({
        type: "put",
        url: `/api/board/modify/${writeId}`,
        data: data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json"

    }).done(res=>{
        console.log("성공", res);
        location.href = `/board/${writeId}`;

    }).fail(error=>{
        console.log(error);
        if(error.data == null) {
            alert(error.responseText);
        }else {
            error.responseText;
        }
    });

}

function writesDelete(writeId, event) {
    event.preventDefault();

    $.ajax({
        type: "delete",
        url: `/api/board/delete/${writeId}`,

    }).done(res=>{
        console.log("성공", res);
        location.href = `/board`;

    }).fail(error=>{
        console.log(error);
        if(error.data == null) {
            alert(error.responseText);
        }else {
            error.responseText;
        }
    });

}