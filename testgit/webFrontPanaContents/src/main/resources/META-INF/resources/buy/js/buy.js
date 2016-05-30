//============================
//
// buy.js
// Last Update 2015-02-24
//
//============================

var mobileThumbWidth = 0;
var isMobile = false;

jQuery.event.add(window, "load", function(){

  /**
   *  checkUserAgent
   *
   ------------------------------- */
  if((navigator.userAgent.indexOf("iPhone") != -1)||((navigator.userAgent.indexOf("Android") != -1)&&(navigator.userAgent.indexOf("Mobile") != -1))||(navigator.userAgent.indexOf('iPod')  !=-1)){
      isMobile = true;
  }


  /**
   *  set carousels
   *
  ------------------------------- */
  var $sliderBlock = $('.sliderBlock');
  $sliderBlock.each( function () {

      // DOMs
      var $slider = $(this).find('.slider'),
          $sliderImg = $slider.find('img'),
          $sliderNext = $slider.find('.arrNext a'),
          $sliderPrev = $slider.find('.arrPrev a'),
          $sliderCaption = $slider.find('.caption'),
          $sliderImgLink = $slider.find('.sliderImgLink'),
          $thumbs = $(this).find('.thumbs'),
          $thumbsNext = $thumbs.find('.arrNext a'),
          $thumbsPrev = $thumbs.find('.arrPrev a'),
          $thumbsList = $thumbs.find('.thumbsList'),
          $thumbsListInner = $thumbsList.find('ul'),
          $thumbsImages = $thumbsList.find('li');

      // member variables
      var currentImageNum = 0,
          currentPageNum = 0,
          thumbsHeight,
          thumbsWidth,
          oneThumbWidth = 74,							// for calculating carousel width for mobile
          slideSpeed = 150;

      // calculate number of thumbnails per a page
      var thumbsPerPage = 6;
      var maxPageNum;

      // initialization
      function init(){

          // calculate number of thumbnails per a page
          thumbsHeight = $thumbsList.height() - 18;		// minus padding-top:18px;
          thumbsWidth = $thumbsList.width() - 42;			// minus width of next & prev arrows,
          if (isMobile) {

              if(!mobileThumbWidth){ 						// タブの部分を隠している影響で幅が取れない。最初に取得できた幅をコピーして利用
                  mobileThumbWidth = thumbsWidth;
              } else {
                  thumbsWidth = mobileThumbWidth;
              }

              thumbsPerPage = Math.floor(thumbsWidth / oneThumbWidth);
			  maxPageNum = Math.ceil($thumbsImages.length - 3);
          }else{
			  //maxPageNum = Math.floor($thumbsImages.length / thumbsPerPage);
			  maxPageNum = Math.ceil($thumbsImages.length / 2 - 3);
		  }

          // set light box
          var $firstThumbLink = $($thumbsImages[currentImageNum]).find('a');
          setLightBoxAnchor($sliderImg, $firstThumbLink.attr('href'), $firstThumbLink.attr('title') );

      }
      init();

      $(window).resize( init );


      // click events
      $sliderPrev.click( function ( e ) {
		  
          e.preventDefault();
          currentImageNum ++; // html での next と prev が逆なため、prev でインクリメント
          if(currentImageNum > $thumbsImages.length - 1){ currentImageNum = 0; }
          if($(this).closest(".sliderBlock").find(".thumbsList li").eq(currentImageNum).find("a").attr("class") == "youtubeThumb"){
            var youtubeUrl = $(this).closest(".sliderBlock").find(".thumbsList li").eq(currentImageNum).find("a").attr("href");
            $(this).closest(".sliderBlock").find(".imageBlock").css("display","none");
            $(this).closest(".sliderBlock").find(".caption").css("display","none");
            $(this).closest(".sliderBlock").find(".youtubeBlock").css("display","block");
            $(this).closest(".sliderBlock").find(".youtubeBlock iframe").attr("src",youtubeUrl);
          }else{
            $(this).closest(".sliderBlock").find(".imageBlock").css("display","table");
            $(this).closest(".sliderBlock").find(".caption").css("display","block");
            $(this).closest(".sliderBlock").find(".youtubeBlock").css("display","none");
            $(this).closest(".sliderBlock").find(".youtubeBlock iframe").attr("src","");
          }
          loadImage(currentImageNum, 'left');
		  
          if(isMobile){
			  var pNum = currentImageNum;
          }else{
			  var pNum = Math.floor((currentImageNum) / 2);
          }
		  if(pNum <= maxPageNum){
			currentPageNum = pNum;
			if(currentPageNum > maxPageNum){ currentPageNum = 0 }
			slideThumbs(currentPageNum);
		  }
      });

      $sliderNext.click( function ( e ) {
          e.preventDefault();
          currentImageNum --; // html での next と prev が逆なため、next でデクリメント
          if(currentImageNum < 0){ currentImageNum = $thumbsImages.length - 1; }
          if($(this).closest(".sliderBlock").find(".thumbsList li").eq(currentImageNum).find("a").attr("class") == "youtubeThumb"){
            var youtubeUrl = $(this).closest(".sliderBlock").find(".thumbsList li").eq(currentImageNum).find("a").attr("href");
            $(this).closest(".sliderBlock").find(".imageBlock").css("display","none");
            $(this).closest(".sliderBlock").find(".caption").css("display","none");
            $(this).closest(".sliderBlock").find(".youtubeBlock").css("display","block");
            $(this).closest(".sliderBlock").find(".youtubeBlock iframe").attr("src",youtubeUrl);
          }else{
            $(this).closest(".sliderBlock").find(".imageBlock").css("display","block");
            $(this).closest(".sliderBlock").find(".caption").css("display","block");
            $(this).closest(".sliderBlock").find(".youtubeBlock").css("display","none");
            $(this).closest(".sliderBlock").find(".youtubeBlock iframe").attr("src","");
          }
          loadImage(currentImageNum, 'right');
		  
          if(isMobile){
			  var pNum = currentImageNum;
          }else{
			  var pNum = Math.floor((currentImageNum) / 2);
          }
		  if(pNum >= 0){
			currentPageNum = pNum;
			if(currentPageNum > maxPageNum){ currentPageNum = maxPageNum }
			slideThumbs(currentPageNum);
		  }
      });

      $thumbsImages.click( function ( e ) {
          if($(this).find("a").attr("class") == "youtubeThumb"){
            e.preventDefault();
            var oldNum = currentImageNum;
            currentImageNum = $thumbsImages.index( this );
            if (oldNum === currentImageNum) { return; }
            var youtubeUrl = $(this).find("a").attr("href");
            $(this).closest(".sliderBlock").find(".imageBlock").css("display","none");
            $(this).closest(".sliderBlock").find(".caption").css("display","none");
            $(this).closest(".sliderBlock").find(".youtubeBlock").css("display","block");
            $(this).closest(".sliderBlock").find(".youtubeBlock iframe").attr("src",youtubeUrl);
            $(this).closest(".sliderBlock").find(".thumbsList li").each(function(){
              $(this).removeClass("current");
            });
            //$(this).addClass("current");
            //return false;
            loadImage( currentImageNum, ( oldNum > currentImageNum ) ? 'right' : 'left' );
          }else{
            e.preventDefault();
            var oldNum = currentImageNum;
            currentImageNum = $thumbsImages.index( this );
            if (oldNum === currentImageNum) { return; }
            $(this).closest(".sliderBlock").find(".imageBlock").css("display","block");
            $(this).closest(".sliderBlock").find(".caption").css("display","block");
            $(this).closest(".sliderBlock").find(".youtubeBlock").css("display","none");
            $(this).closest(".sliderBlock").find(".youtubeBlock iframe").attr("src","");
            loadImage( currentImageNum, ( oldNum > currentImageNum ) ? 'right' : 'left' );
          }
      });

      $thumbsPrev.click( function ( e ) {
          e.preventDefault();
          currentPageNum ++; // html での next と prev が逆なため、prev でインクリメント
          if(currentPageNum > maxPageNum){ currentPageNum = 0 }
          slideThumbs(currentPageNum);
      });

      $thumbsNext.click( function ( e ) {
          e.preventDefault();
          currentPageNum --; // html での next と prev が逆なため、next でデクリメント
          if(currentPageNum < 0){ currentPageNum = maxPageNum }
          slideThumbs(currentPageNum);
      });


      // helper functions
      function loadImage(num, dir){
          switchCurrentClass(num);

          var comingThumb = $($thumbsImages[num]).find('img').attr('src'),
              comingImage = comingThumb.replace('/200/', '/800/'),
              comingTitle = $($thumbsImages[num]).find('a').attr('title');

          var bigImageUrl = $($thumbsImages[num]).find('a').attr('href');

          if(bigImageUrl && bigImageUrl !== '#'){
              setLightBoxAnchor($sliderImg, bigImageUrl, comingTitle);
          }

          (dir === 'left')? slideOutLeft($sliderImg) : slideOutRight($sliderImg);

          $sliderCaption.stop().fadeTo(slideSpeed, 0);
          setTimeout(function(){
              $sliderImg.load(function(){
                  slideIn($sliderImg, 1);
                  setCaption(comingTitle, num);
              }).attr({
                  "src" : comingImage
              });
          }, slideSpeed + 50);
      }

      function setCaption ( str,num ) {
        if($($thumbsImages[num]).find('a').attr('class') != 'youtubeThumb'){
          if ( str ) {
              $sliderCaption.text( str );
              $sliderCaption.stop().fadeTo(150, 1);
          } else {
              $sliderCaption.stop().fadeTo(150, 0);
          }
        }else{
          $sliderCaption.css("display","none");
        }
      }

      function switchCurrentClass ( num ) {
          $thumbsImages.removeClass('current');
          $($thumbsImages[num]).addClass('current');

          if(Math.floor(num / thumbsPerPage) != currentPageNum){
              //currentPageNum = Math.floor(num / thumbsPerPage);
              //slideThumbs(currentPageNum);
          }
      }

      function slideOutLeft ( image ) {
          image.stop().animate({
              //'margin-left' : '-100%',
              'opacity' : '0'
          }, slideSpeed, function () {
              //$(this).css({ 'margin-left' : '100%' });
          });
      }
      function slideOutRight ( image ) {
          image.stop().animate({
              //'margin-left' : '100%',
              'opacity' : '0'
          }, slideSpeed, function(){
              //$(this).css({ 'margin-left' : '-100%' });
          });
      }
      function slideIn ( image, opacity ) {
          image.stop().animate({
              //'margin-left' : '0',
              'opacity' : opacity
          }, slideSpeed);
      }

      function slideThumbs ( num ) {
          if (isMobile) {
              if(num == maxPageNum){
                var thumbsWidth = (oneThumbWidth) * num * -1;
              }else if(num == 0){
                var thumbsWidth = 0;
              }else if(num > 0) {
                var thumbsWidth = (oneThumbWidth) * num * -1;
              }else{
                var thumbsWidth = (oneThumbWidth) * num;
              }
              $thumbsListInner.animate( {
                  //'margin-left' : ( -1 * num * oneThumbWidth * thumbsPerPage - 1) + 'px'
                'margin-left' : ( thumbsWidth ) + 'px'
              }, 200);
          } else {
              //var thumbsBlockHeight = parseInt($(".thumbsList ul").css("marginTop"),10);
              //var thumbsHeight = thumbsBlockHeight + $(".thumbsList li").height() + 22;
              //var thumbsMove = maxPageNum - num;

              if(num == maxPageNum){
                var thumbsHeight = ($(".thumbsList li").height() + 21) * num * -1;
              }else if(num == 0){
                var thumbsHeight = 0;
              }else if(num > 0) {
                var thumbsHeight = ($(".thumbsList li").height() + 21) * num * -1;
              }else{
                var thumbsHeight = ($(".thumbsList li").height() + 21) * num;
              }
              $thumbsListInner.animate( {
                  //'margin-top' : ( -1 * num * thumbsHeight ) + 'px'
                'margin-top' : ( thumbsHeight ) + 'px'
              }, 200);
          }
      }

      function setLightBoxAnchor ( image, imageUrl, title ) {
          $sliderImgLink.attr('href', imageUrl);
          $sliderImgLink.attr('title', title);

          if(imageUrl.indexOf('youtube') > 0){
              $sliderImgLink.addClass('fancybox.iframe');
          } else {
              $sliderImgLink.removeClass('fancybox.iframe');
          }
		  
		  $thumbsImages.find('a').hover(
			function () {
				  $thumbsImages.find('a').removeClass('fancybox');
				  $thumbsImages.find('a').removeClass('fancybox.iframe');
			},
			function () {
			}
		  );

          $sliderImgLink.unbind('click');
          $sliderImgLink.unbind('click.fb-start');
          $sliderImgLink.click(function(e){
              if (!isMobile) {

                  e.preventDefault();
                  var fancyboxOption = {openSpeed:0,closeSpeed:0,nextSpeed:0,prevSpeed:0};

                  fancyboxOption.beforeClose = function () {
                      //$thumbsImages.find('a').removeClass('fancybox');
                      //$thumbsImages.find('a').removeClass('fancybox.iframe');
                  };

                  $thumbsImages.find('a').each(function(){
                      $this = $(this);
                      $this.addClass('fancybox');
                      if ( $this.attr('href').indexOf('youtube') > 0) {
                          $this.addClass('fancybox.iframe');
                      }
                  });

                  $('.fancybox').fancybox( fancyboxOption );
                  $($thumbsImages[currentImageNum]).find('a').click();

              } else {

                  if ( $(this).attr('href').indexOf('youtube') > 0) {

                  } else {
                      e.preventDefault();
                  }

              }

          });
      }
  }); //  END  $sliderBlock.each
});

$(document).ready(function() {
  // make sliderBox clickable after all images loaded
  var $sliderBlock = $('.sliderBlock');
  $sliderBlock.append('<div class="beforeLoad"></div>');

  $('.beforeLoad').fadeTo(1000, 0);
  setTimeout(function(){
      $('.beforeLoad').css({ 'display' : 'none' });
  }, 1000);

  /**
   *  set tabs
   *
   ------------------------------- */
  var $tabs = $('.tabMenu'),
      $tabButtons = $tabs.find('li'),
      $tabButtonAfter = $($tabButtons[0]).find('a'),
      $tabButtonBefore = $($tabButtons[1]).find('a'),
      $afterImages = $('#after'),
      $beforeImages = $('#before');

  $beforeImages.css({
      'display' : 'none',
      'height' : 'inherit'
  });

  $tabButtonAfter.click(function(e){
      e.preventDefault();
      $beforeImages.css({ 'display' : 'none' });
      $afterImages.css({ 'display' : 'block' });
      $tabButtons.removeClass('current');
      $(this).parent().addClass('current');
  });

  $tabButtonBefore.click(function(e){
      e.preventDefault();
      $beforeImages.css({ 'display' : 'block' });
      $afterImages.css({ 'display' : 'none' });
      $tabButtons.removeClass('current');
      $(this).parent().addClass('current');
  });
});

$(window).on("load resize", function(){
  if (isMobile) {
    $(".youtubeBlock iframe").css("height",$(".imageBlock").height());
  }
});

