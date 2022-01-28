function writesDelete(writeId, event, pageNum) {
    event.preventDefault();

    var result = confirm("정말 삭제하시겠습니까?");

    if(result) {
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
    }else {

    }


}

function imageDelete(index, imageFileName, event) {
    event.preventDefault();

    $(`#images-${index}`).remove();
    $(`#deleteButton-${index}`).remove();
    let content = `<input type="hidden" name="deleteImageNames" value="${imageFileName}">`;
    $("#imageList").prepend(content);
}