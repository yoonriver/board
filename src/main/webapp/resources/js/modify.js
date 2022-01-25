//function writesModify(writeId, event, pageNum) {
//
//    event.preventDefault();
//
//    let data = $("#writesModify").serialize();
//    var totalFiles = document.getElementById('fileList').files.length;
//    console.log(totalFiles);
//    for (var i = 0; i < totalFiles; index++) {
//       form_data.append("fileList[]", document.getElementById('fileList').files[i]);
//    }
//
//    $.ajax({
//        type: "POST",
//        url: `/api/board/modify/${writeId}`,
//        data: data,
//        contentType: "application/x-www-form-urlencoded; charset=utf-8",
//        dataType: "json"
//
//    }).done(res=>{
//        console.log("성공", res);
//        location.href = `/board/${writeId}?page=${pageNum}`;
//
//    }).fail(error=>{
//        console.log(error);
//        if(error.data == null) {
//            alert(error.responseText);
//        }else {
//            error.responseText;
//        }
//    });
//
//}

function writesDelete(writeId, event, pageNum) {
    event.preventDefault();

    $.ajax({
        type: "delete",
        url: `/api/board/delete/${writeId}`,

    }).done(res=>{
        console.log("성공", res);
        location.href = `/board/list?page=${pageNum}&keyword=&option=&category=`;

    }).fail(error=>{
        console.log(error);
        if(error.data == null) {
            alert(error.responseText);
        }else {
            error.responseText;
        }
    });

}

function imageDelete(index, imageFileName, event) {
    event.preventDefault();

    $(`#images-${index}`).remove();
    $(`#deleteButton-${index}`).remove();
    let content = `<input type="hidden" name="deleteImageNames" value="${imageFileName}">`;
    $("#imageList").prepend(content);
}