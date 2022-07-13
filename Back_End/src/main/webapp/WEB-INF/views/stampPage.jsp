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
						param : param,
						completionStamp : "${shopInfo.COMPLETION_STAMP}",
						userStampList: "${userStampList}"
					},
					success:function(data){
						window.location.reload();
						
						
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
           <a class="title">스탬프 확인</a>
       </header> 
        <div id="container">
            <div class="ph_shop" style="padding-bottom: 15px;">
                <p>${shopInfo.SHOP_NAME} (${shopInfo.SHOP_BRANCH})  </p>
                <p>사업자번호 ${shopInfo.SHOP_BIZNO}</p>
                <p>${shopInfo.SHOP_TEL_NUM} ${shopInfo.SHOP_CEO}</p>
                <p>${shopInfo.SHOP_ADDR}</p>
                
            </div>
           <div class="ticket">
                		<!-- 회원번호 2934 -->
            </div>	   
   			
         <%--    <div><%= (153 -30) - (153/10 * 0)  %><div>
   			<div>${marginLeft}</div>
   			<br/>
   			<div><%=153 * 0/10 %><div>
   			<div>${stampPosition}</div>
   			<br/>
   			<div>-<%=153 * 10/10%><div>
   			<div>${unStampPosition}</div>
   			
		 	<div class="stamp" style=" margin-left : <%= (153 -30) - (153/10 * 0)  %>px;">
	
				<div style="width : 153px; height : 150px;">
					<img src="/resources/images/stamp_blue.png"
						style="width : 153px; height : 150px; object-fit : cover; object-position: <%=153 * 0 /10 %>px 0%;"
					>
				</div>
				<div style=" width : 153px; height : 150px;">
					<img src="/resources/images/stamp_gray1.png"
						style="width : 153px; height : 150px; object-fit : cover; object-position: -<%=153 * 10/10%>px 0%; "
					>
				</div>
				
			</div>      --%>              
			<div class="stamp" style=" margin-left : ${marginLeft}px; margin-bottom : 20px;">
	
			<div style="width : 153px; height : 150px;">
					<img src="/resources/images/stamp_blue.png"
						style="width : 153px; height : 150px; object-fit : cover; object-position: ${stampPosition}px 0%;"
					>
				</div>
				<div style=" width : 153px; height : 150px;">
					<img src="/resources/images/stamp_gray1.png"
						style="width : 153px; height : 150px; object-fit : cover; object-position: ${unStampPosition}px 0%; "
					>
				</div>
			
			</div> 
				<div style="position : relative; ">
					<div style="font-size : 40px; position : absolute; width : 150px; right : 30px; bottom : 17px;">${stampCount} / ${shopInfo.COMPLETION_STAMP}</div>
				</div>	
      
            
            </div>  
            <div>
            	${userStampList}
            </div>
            <c:if test="${stampCount  >= shopInfo.COMPLETION_STAMP}">
               <div class="buttonContainer">
            		<button class="useButton" onclick="useButton()">사용하기</button>
           	    </div>
            </c:if>
          
             
        </div><!--#container-->
    </div><!--#wrap-->
</body>
</html>