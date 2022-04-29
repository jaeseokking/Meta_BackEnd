<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
 <link rel="stylesheet" href="./resources/css/reset.css">
    <link rel="stylesheet" href="./resources/css/style.css">
<title>윷놀이</title>
<script>
$(document).ready(function(){
	var one = 0; 
	var two = 0;
	var three = 0;
	var four = 0;
	var five = 0;
	
	$("#play").click(function(){
		
		if($(".num").attr("value") =="1"){
		
		 $(".num").attr("value","0"); //게임횟수 = 0
		 $(".num").text("0"); //게임횟수 = 0
			 
		 $("#pictures").attr("src","./resources/img/shut2.gif"); //시작하면 윷돌아가는 이미지 재생
			
		setCookie('AQQQWEEWRRR', 'QWERRFDSASDEVENTSS', 1); //쿠키생성
		 
		var pick = Math.floor(Math.random()*1000); //랜덤 수 생성
		
		if(pick<300){ // 300까지는 낙 30%
			
			let promise = new Promise(function(resolve, reject){
				  setTimeout(function(){
					  $("#pictures").css("padding","20px 0");
					  $("#pictures").attr("src","./resources/img/yut_backdoe.png"); //백도 일반이미지
					  resolve();
				  }, 800);
				});
			 
				promise.then(function(){
					setTimeout(function(){
						 $("#pictures").css("padding","0 0");
						  $("#pictures").attr("src","./resources/img/backdoe2.gif"); //0.8초뒤 백도이미지 나옴
					  }, 800);
				});

		
		}else if(pick<644){ //도
			
			$.ajax({ //도 갯수 확인하기 
				type:"post",
				url:"/getCount.do",
				data:{num:"one"},
			  success:function(data){
				 one = data.one; //DB에서 도갯수 가져온거
				
				 if(one<60){ // 도의 개수가 60개가 넘지않으면
					 
					 $.ajax({
							type:"post",
							url:"/addCount.do", //DB에 도 개수 1올리기
							data:{num:"one"},
						  success:function(data){
							 var result=data.result 
							 if(result != 0){
								 
								 let promise = new Promise(function(resolve, reject){
									  setTimeout(function(){
										  $("#pictures").css("padding","20px 0");
										  $("#pictures").attr("src","./resources/img/yut_doe.png");
										  resolve();
									  }, 800);
									});
								 
									promise.then(function(){
										setTimeout(function(){
											 $("#pictures").css("padding","0px 0");
											  $("#pictures").attr("src","./resources/img/doe2.gif");
										  }, 800);
									});
								
							 }
						  }
						});
					}else{ //도의 갯수가 60개가 이상이면
						let promise = new Promise(function(resolve, reject){
							  setTimeout(function(){
								  $("#pictures").css("padding","20px 0");
								  $("#pictures").attr("src","./resources/img/yut_backdoe.png");
								  resolve();
							  }, 800);
							});
						 
							promise.then(function(){
								setTimeout(function(){
									 $("#pictures").css("padding","0 0");
									  $("#pictures").attr("src","./resources/img/backdoe2.gif");
								  }, 800);
							});
					}
			  }
			});
		
		}else if(pick<816){ //개
			$.ajax({
				type:"post",
				url:"/getCount.do",
				data:{num:"two"},
			  success:function(data){
				 two = data.two; //DB에서 개 개수 가져온거
				 if(two<30){
					  $.ajax({
							type:"post",
							url:"/addCount.do", //DB에 개 개수 1올리기
							data:{num:"two"},
						  success:function(data){
							 var result=data.result 
							 if(result != 0){
								 let promise = new Promise(function(resolve, reject){
									  setTimeout(function(){
										  $("#pictures").attr("src","./resources/img/yut_gae.png");
										  resolve();
									  }, 800);
									});
								 
									promise.then(function(){
										setTimeout(function(){
											  $("#pictures").attr("src","./resources/img/gae2.gif");
										  }, 800);
									});
							 }
						  }
						});
					  
					}else{ //개 갯수가 30이상이면
						let promise = new Promise(function(resolve, reject){
							  setTimeout(function(){
								  $("#pictures").attr("src","./resources/img/yut_backdoe.png");
								  resolve();
							  }, 800);
							});
						 
							promise.then(function(){
								setTimeout(function(){
									  $("#pictures").attr("src","./resources/img/backdoe2.gif");
								  }, 800);
							});
					}
			  }
			});
		}else if(pick<902){ //걸
			$.ajax({
				type:"post",
				url:"/getCount.do", //DB에서 걸 개수 가져오기
				data:{num:"three"},
			  success:function(data){
				 three = data.three; 

					if(three<15){
						 $.ajax({
								type:"post",
								url:"/addCount.do", //DB 걸 개수 1올리기
								data:{num:"three"},
							  success:function(data){
								 var result=data.result 
								 if(result != 0){
									 let promise = new Promise(function(resolve, reject){
										  setTimeout(function(){
											  $("#pictures").attr("src","./resources/img/yut_girl.png");
											  resolve();
										  }, 800);
										});
									 
										promise.then(function(){
											setTimeout(function(){
												  $("#pictures").attr("src","./resources/img/girl2.gif");
											  }, 800);
										});
								 }
							  }
							});
						}else{
							let promise = new Promise(function(resolve, reject){
								  setTimeout(function(){
									  $("#pictures").attr("src","./resources/img/yut_backdoe.png");
									  resolve();
								  }, 800);
								});
							 
								promise.then(function(){
									setTimeout(function(){
										  $("#pictures").attr("src","./resources/img/backdoe2.gif");
									  }, 800);
								});
						}
				 
			  }
			});
			
			
		}else if(pick<960){ //윷
			$.ajax({
				type:"post",
				url:"/getCount.do", //DB에서 윷 개수 가져오기
				data:{num:"four"},
			  success:function(data){
				 four = data.four;
				 if(four<10){
					  $.ajax({
							type:"post",
							url:"/addCount.do", //DB에 윷 개수 1 증가
							data:{num:"four"},
						  success:function(data){
							 var result=data.result 
							 if(result != 0){
								 let promise = new Promise(function(resolve, reject){
									  setTimeout(function(){
										  $("#pictures").attr("src","./resources/img/yut_yut.png");
										  resolve();
									  }, 800);
									});
								 
									promise.then(function(){
										setTimeout(function(){
											  $("#pictures").attr("src","./resources/img/yut2.gif");
										  }, 800);
									});
							 }
						  }
						});
					  
						}else{
							let promise = new Promise(function(resolve, reject){
								  setTimeout(function(){
									  $("#pictures").attr("src","./resources/img/yut_backdoe.png");
									  resolve();
								  }, 800);
								});
							 
								promise.then(function(){
									setTimeout(function(){
										  $("#pictures").attr("src","./resources/img/backdoe2.gif");
									  }, 800);
								});
						}
			  }
			});
			
			
		}else if(pick<1000){ //모
			$.ajax({
				type:"post",
				url:"/getCount.do", //DB에서 모 개수 가져오기
				data:{num:"five"},
			  success:function(data){
				 five = data.five;
				 
				 if(five<7){
					  $.ajax({
							type:"post",
							url:"/addCount.do", //DB에 모 개수 1 증가
							data:{num:"five"},
						  success:function(data){
							 var result=data.result 
							 if(result != 0){
								 let promise = new Promise(function(resolve, reject){
									  setTimeout(function(){
										  $("#pictures").attr("src","./resources/img/yut_moe.png");
									  }, 800);
									});
								 
									promise.then(function(){
										setTimeout(function(){
											  $("#pictures").attr("src","./resources/img/moe2.gif");
										  }, 800);
									});
							 }
						  }
						});
						  
						}else{
							
							let promise = new Promise(function(resolve, reject){
								  setTimeout(function(){
									  $("#pictures").attr("src","./resources/img/yut_backdoe.png");
								  }, 800);
								});
							 
								promise.then(function(){
									setTimeout(function(){
										  $("#pictures").attr("src","./resources/img/backdoe2.gif");
									  }, 800);
								});
						  
						}
				 
			 		 }
				});
			}
		
		} //if 당일참여횟수가 1이면 게임가능
		else if($(".num").attr("value") == "0"){ //조회하기 수가 0이면
			alert("당일 참여 완료!");
		}else{ // ? 라면
			alert("당일 참여횟수를 조회 해주세요!");
		}
	});//play 윷놀이 한판하기 
	
	
	
	$("#searchCount").click(function(){ //조회하기
		
	 var cookies = getCookie("AQQQWEEWRRR"); //쿠키 가져오기 
	 
	 if(cookies != null){ //쿠키가 있다면
		 $(".num").attr("value","0"); //게임횟수 = 0
		 $(".num").text("0"); //게임횟수 = 0
	 }else{
		 //제대로된 조회확인으로 수정하셔야합니다. 임시로 쿠키로 제작함
		 $(".num").attr("value","1"); //게임횟수 = 1
		 $(".num").text("1"); //게임횟수 = 1
	 }
	 
		
	});//searchCount 당일 참여 가능횟수 확인하기
	
	
	



	



	
	
	
	
});//ready

function setCookie(name, value, exp) { //쿠키생성
	  var date = new Date();
	  date.setTime(date.getTime() + exp*24*60*60*1000);
	  document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + ';path=/';
	}

	
function getCookie(name) { //쿠키가져오기
		  var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
		  return value? value[2] : null;
	}
	

</script>
</head>
<body>
   <div id="wrap">
       <div id="container">
           
            <div class="yut_top_img">
                <img src="./resources/img/yut_top.png" alt="상단탑이미지">
            </div><!--#yut_top_img-->
            
            <div class="inquire">
                <a id="searchCount">
                    <img src="./resources/img/inquire.png" alt="당일참여가능횟수조회하기">
                </a>
                <div class="inquire_count">
                   <u class="num">?</u>회  
                </div>
            </div><!--#조회하기버튼-->
            
            <div class="inquire_explanation">
                <img src="./resources/img/inquire_explanation.png" alt="ID당 1일 1회 참여 기간내 5회 참여 가능합니다.">
            </div><!--#조회하기설명글-->
            
            <div class="yut_gif">
                <img src="./resources/img/yut_gif.png" alt="윷놀이 gif" id="pictures">
            </div><!--#yut_gif-->
            
            <div class="yut_play">
                <a id="play">
                    <img src="./resources/img/yut_play.png" alt="윷놀이 한판하기">
               </a>
            </div><!--#yut_play-->
            
            <div class="yut_bottom_img">
                <img src="./resources/img/yut_bottom.png" alt="윷놀이 바닥이미지">
            </div><!--#yut_bottom_img-->
            
       </div><!--#container-->
   </div><!--#wrap-->
</body>
</html>