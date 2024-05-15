
const deleteButton =document.getElementById('delete-button');

if(deleteButton){
    deleteButton.addEventListener('click',event => {
        var id= document.getElementById('guestBook-id').value
        fetch('/api/delete/guestbook/'+id,{
            method: 'DELETE'
        })
            .then(()=>{
                alert('삭제가 완료되었습니다.');
                location.replace(`/guestbookList`)
            });
    });
}

const modifyButton =document.getElementById('modify-button');

if(modifyButton){
    modifyButton.addEventListener('click',event => {

        fetch(`/api/modify/guestBook`,{
            method: 'POST',
            headers:{
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                id: document.getElementById('guestBook-id').value,
                title: document.getElementById('guestBook-title').value,
                content: editor.getHTML(),
                createdAt: document.getElementById('guestBook-createdAt').value,
                updatedAt: document.getElementById('guestBook-updatedAt').value,
                userid: document.getElementById('guestBook-userid').value,
                author: document.getElementById('guestBook-author').value

            })
        })
            .then(()=>{
                alert('수정이 완료되었습니다.');
                location.replace("main");
            });
    });
}

const createButton =document.getElementById('create-btn');

if(createButton){
    createButton.addEventListener('click',event => {

        fetch(`/api/newContent`,{
            method: 'POST',
            headers:{
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: editor.getHTML(),
                userid: "최형석"
            })
        })
            .then(()=>{

                alert('등록 성공!! 최고다 최고');
                location.replace(`/guestbookList`)
            });
    });
}

