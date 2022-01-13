function comment(writeId, event) {

    event.preventDefault();

    let data = $("#comment").serialize();

    $.ajax({
        type: "post",
        url: `/api/comment/${writeId}`,
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

function commentReply(writeId, event) {

    event.preventDefault();

    let data = $("#comment").serialize();

    $.ajax({
        type: "post",
        url: `/api/comment/reply/${writeId}`,
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

function commentModify(commentId, writeId, event) {

    event.preventDefault();

    let data = $("#comment").serialize();

    $.ajax({
        type: "put",
        url: `/api/comment/modify/${commentId}`,
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

function commentDelete(commentId, writeId, event) {
    event.preventDefault();

    $.ajax({
        type: "delete",
        url: `/api/comment/delete/${commentId}`,

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