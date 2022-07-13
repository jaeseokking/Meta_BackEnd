<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>스탬프 확인</title>
    <link rel="stylesheet" href="/resources/css/style.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <script src="/resources/static/jquery-3.6.0.min.js"></script>
    <script src="/resources/static/modernizr.js"></script>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
</head>
<script>
	function searchParam(key){
		return new URLSearchParams(location.search).get(key);
	}


	function useButton(){
		if(confirm("사용하시겠습니까?") == true){
			const param = searchParam("param");
			if(param != null && param != ""){
				$.ajax({ 
					url : "api/stamp/use",
					type : 'POST',
					data : {
						param : param
					},
					success:function(data){
						
						
						
					}
			
				})  
			}
			
			
		}
	}
</script>


<body>
   <div id="stamp">
    <div id="wrap">
       <header>
           <a class="title">NOT FOUND PAGE</a>
       </header> 
        <div id="container">
            <div class="ph_shop" style="padding-bottom: 15px;">
                <p>잘못된 URL 주소입니다.</p>
               
            </div>  
   			
            </div>  
            	
             
        </div><!--#container-->
    </div><!--#wrap-->
</body>
</html>