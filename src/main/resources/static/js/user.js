let index = {
    init: function () {
        $("#btn-save").on("click", ()=>{ // function(){} 대신 ()=>{}를 쓰는 이유는 this를 바인딩하기 위해서이다
            // 만약 function(){} 쓰면, init: function()의 this 값과 현재 내부의 function() this 값이 다를 수 있음
            // 현재 위치의 function() this 값이 window 객체를 가르킬 수 도 있음
            this.save();
        });
    },

    save: function() {
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };

        // ajax 호출시 default가 비동기 호출
        // ajax 통신을 이용해서 3 개의 데이터를 json으로 변경하여 insert 요청
        // ajax가 통신 성공하고 서버가 json 리턴해주면 자동으로 자바 오브젝트로 변환해줌
        $.ajax({
            type: "POST",
            url: "/blog/api/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
            dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript 오브젝트로 변경해
        }).done(function(resp) {
            alert("회원가입이 완료되었습니다.");
            console.log(resp);
            location.href= "/blog";
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    }
}

index.init();