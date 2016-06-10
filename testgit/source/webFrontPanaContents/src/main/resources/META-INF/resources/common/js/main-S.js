	// 画像を含むドキュメントの読み込みが完了した後に実行
	jQuery.event.add(window, "load", function(){
		
		// Back to top
		//--------------------------------
		$("a[href*='#']").slideScroll();
		
	});//END
	
	
	/* 印刷
	-----------------------*/
	function print_new(){
	window.print();
	}


	/* 画像ロールオーバー
	-----------------------*/
	function smartRollover() {
		if(document.getElementsByTagName) {
			var images = document.getElementsByTagName("img");
	
			for(var i=0; i < images.length; i++) {
				if(images[i].getAttribute("src").match("_off."))
				{
					images[i].onmouseover = function() {
						this.setAttribute("src", this.getAttribute("src").replace("_off.", "_on."));
					}
					images[i].onmouseout = function() {
						this.setAttribute("src", this.getAttribute("src").replace("_on.", "_off."));
					}
				}
			}
		}
	}
	
	if(window.addEventListener) {
		window.addEventListener("load", smartRollover, false);
	}
	else if(window.attachEvent) {
		window.attachEvent("onload", smartRollover);
	}
	
	
	//PC/SPアコーディオン
	//--------------------------------
	$(function(){
		 
		$(".cmn_accordion_button").click(function(){
			 //スライドの処理
			if($(this).next('.box_cmn_accordion').is(':visible')) {
				$(this).next('.box_cmn_accordion').slideUp(300);
			} else {
				$(this).next('.box_cmn_accordion').slideDown(300).siblings('.box_cmn_accordion').slideUp(300);
			}
			$(this).toggleClass("current");
			$(this).children().toggleClass("open");
		}); 
	 
	});
	
	
	//別ウィンドウリンク
	//--------------------------------
	function newWin(url,windowname,width,height) {
		var features="location=no, menubar=no, status=yes, scrollbars=yes, resizable=yes, toolbar=no";
			if (width) {
				if (window.screen.width > width)
				features+=", left="+(window.screen.width-width)/0;
				else width=window.screen.width;
				features+=", width="+width;
			}
			if (height) {
				if (window.screen.height > height)
				features+=", top="+(window.screen.height-height)/0;
				else height=window.screen.height;
				features+=", height="+height;
			}
		window.open(url,windowname,features);
	}
	
	
	;(function($){
	
	/**********************************
	// Plugins
	//**********************************
	/*---------------------------------------------------------------
	jQuery.slideScroll.js
	
	jQuery required (tested on version 1.2.6)
	encoding UTF-8
	
	Copyright (c) 2008 nori (norimania@gmail.com)
	http://moto-mono.net
	Licensed under the MIT
	
	$Update: 2008-12-24 20:00
	$Date: 2008-12-22 23:30
	 ----------------------------------------------------------------*/
	$.fn.slideScroll = function(options){
		
		var c = $.extend({
			interval: 20,
			easing: 1.0, // 0.4 ~ 2.0 
			comeLink: false
		},options);
		var d = document;
		
		var timer;
		var pos;
		
		function currentPoint(){
			var current = {
				x: d.body.scrollLeft || d.documentElement.scrollLeft,
				y: d.body.scrollTop || d.documentElement.scrollTop
			}
			return current;
		}
		
		function setPoint(){
			var h = d.documentElement.clientHeight;
			var w = d.documentElement.clientWidth;
			var maxH = d.documentElement.scrollHeight;
			var maxW = d.documentElement.scrollWidth;
			
			pos.top = ((maxH-h)<pos.top && pos.top<maxH) ? maxH-h : pos.top;
			pos.left = ((maxW-w)<pos.left && pos.left<maxW) ? maxW-w : pos.left;
		}
		
		function nextPoint(){
			var x = currentPoint().x;
			var y = currentPoint().y;
			var sx = Math.ceil((x - pos.left)/(5*c.easing));
			var sy = Math.ceil((y - pos.top)/(5*c.easing));
			var next = {
				x: x - sx,
				y: y - sy,
				ax: sx,
				ay: sy
			}
			return next;
		}
		
		function scroll(){
			timer = setInterval(function(){
				nextPoint();
				
				if(Math.abs(nextPoint().ax)<=1 && Math.abs(nextPoint().ay)<=1){
					clearInterval(timer);
					window.scroll(pos.left,pos.top);
				}
				window.scroll(nextPoint().x,nextPoint().y);
			},c.interval);
		}
		
		function comeLink(){
			if(location.hash){
				if($(location.hash) && $(location.hash).length>0){
					pos = $(location.hash).offset();
					setPoint();
					window.scroll(0,0);
					if($.browser.msie){
						setTimeout(function(){
							scroll();
						},50);
					}else{
						scroll();
					}
				}
			}
		}
		if(c.comeLink) comeLink();
		
		$(this).each(function(){
			if(this.hash && $(this.hash).length>0 
				&& this.href.match(new RegExp(location.href.split("#")[0]))){
				var hash = this.hash;
				$(this).click(function(){
					pos = $(hash).offset();
					clearInterval(timer);
					setPoint();
					scroll();
					return false;
				});
			}
		});
	};
})(jQuery);
	
		$(function(){
			$(window).bind("scroll", function() {
			if ($(this).scrollTop() > 150) { 
				$("#pageTop").fadeIn();
			} else {
				$("#pageTop").fadeOut();
			}
			// ドキュメントの高さ
			scrollHeight = $(document).height();
			// ウィンドウの高さ+スクロールした高さ→ 現在のトップからの位置
			scrollPosition = $(window).height() + $(window).scrollTop();
			// フッターの高さ
			footHeight = $("footer").height();
			
			// スクロール位置がフッターまで来たら
			if ( scrollHeight - scrollPosition  <= footHeight ) {
				// ページトップリンクをフッターに固定
				$("#pageTop a").css({"position":"absolute","bottom": "0px"});
			} else {
				// ページトップリンクを右下に固定
				$("#pageTop a").css({"position":"fixed","bottom": "20px"});
				}
			});
		});
		
		$(function(){
			if($(".mnav_buy").length){
				nav = location.pathname
				if(nav.match("/buy/")){
					$('#mainNav nav ul.mainnav li').eq(0).addClass('current');
			　　}
				
			　　nav = location.pathname
				if(nav.match("/history/")){
					$('#mainNav nav ul.mainnav li').eq(0).addClass('current');
			　　}
				if(nav.match("/mypage/favorite/")){
					$('#mainNav nav ul.mainnav li').eq(3).addClass('current');
			　　}
			}
			if($(".snav_buy").length){
				if(location.pathname != "/") {
					var $path = location.href.split('/');
					var $endPath = $path.slice($path.length-2,$path.length-1);
					$('ul.subnav01 li a[href$="'+$endPath+'/"]').parent().addClass('current');
				}
				
			　　nav = location.pathname
				if(nav.match("/buy/re2/")){
					$('#subNav nav ul li').eq(1).addClass('current');
			　　}
				if(nav.match("/buy/reform/")){
					$('#subNav nav ul li').eq(2).addClass('current');
			　　}
				if(nav.match("/buy/payment/")){
					$('#subNav nav ul li').eq(3).addClass('current');
			　　}
			}
			
			if($(".mnav_sell").length){
				nav = location.pathname
				if(nav.match("/sell/")){
					$('#mainNav nav ul.mainnav li').eq(1).addClass('current');
			　　}
			}
			if($(".snav_sell").length){
				if(location.pathname != "/") {
					var $path = location.href.split('/');
					var $endPath = $path.slice($path.length-2,$path.length-1);
					$('ul.subnav01 li a[href$="'+$endPath+'/"]').parent().addClass('current');
				}
				
			　　nav = location.pathname
				if(nav.match("/sell/rearie/")){
					$('#subNav nav ul li').eq(1).addClass('current');
			　　}
				if(nav.match("/sell/flow/")){
					$('#subNav nav ul li').eq(2).addClass('current');
			　　}
			}
			
			if($(".mnav_other").length){
				nav = location.pathname
				if(nav.match("/brand/")){
					$('#mainNav nav ul.mainnav li').eq(2).addClass('current');
			　　}
				if(nav.match("/mypage/")){
					$('#mainNav nav ul.mainnav li').eq(3).addClass('current');
			　　}
				if(nav.match("/mypage_service/")){
					$('#mainNav nav ul.mainnav li').eq(3).addClass('current');
			　　}
				if(nav.match("/mypage")){
					$('#mainNav nav ul.mainnav li').eq(3).addClass('current');
			　　}
			}
		});
	
