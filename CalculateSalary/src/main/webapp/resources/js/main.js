function chgView(ele){

	if(document.getElementById(ele).style.display == "none"){
		document.getElementById(ele).style.display = "block";
	}else{
		document.getElementById(ele).style.display = "none";
	}

}
function chgView2(ele,ele2){

	if(document.getElementById(ele).style.display == "none"){
		document.getElementById(ele).style.display = "block";
		document.getElementById(ele2).style.display = "block";
	}else{
		document.getElementById(ele).style.display = "none";
		document.getElementById(ele2).style.display = "none";
	}

}

function chgView3(ele,ele2,ele3){

	if(document.getElementById(ele).style.display == "none"){
		document.getElementById(ele).style.display = "block";
		document.getElementById(ele2).style.display = "block";
		document.getElementById(ele3).style.display = "block";
	}else{
		document.getElementById(ele).style.display = "none";
		document.getElementById(ele2).style.display = "none";
		document.getElementById(ele3).style.display = "none";
	}

}

function getResult(){
	if(confirm("検索結果が*****件存在します。\n時間がかかる場合がありますが、表示してもよろしいですか？")){
		chgView('search_result');
	}
}

function getExcel(){
	if(confirm("EXCELファイルをダウンロードします。よろしいですか？")){
		alert("EXCELファイルをダウンロードしました。");
	}
}

function searchClear(frm){
	frm.reset();
}

function winClose(){
	if(confirm("終了します。よろしいですか？")){
		window.close();
	}
}

function execView(strViewName){

	var strHtmlName;

	switch (strViewName){
		case "作業時間照会":
			strHtmlName = "P01_作業時間照会.html";
			break;
		case "作業実績集計照会":
			strHtmlName = "P02_作業実績集計照会.html";
			break;
		case "WBS要素照会":
			strHtmlName = "P04_WBS要素照会.html";
			break;
		case "品質管理情報照会":
			strHtmlName = "P05_品質管理情報照会.html";
			break;
		case "WBS実績集計照会":
			strHtmlName = "P06_WBS実績集計照会.html";
			break;
		case "受注明細照会":
			strHtmlName = "S01_受注明細照会.html";
			break;
		case "売上明細照会":
			strHtmlName = "S02_売上明細照会.html";
			break;
		case "請求明細照会":
			strHtmlName = "S03_請求明細照会.html";
			break;
		case "売上合計照会":
			strHtmlName = "S04_売上合計照会.html";
			break;
		case "受注残高照会":
			strHtmlName = "S05_受注残高照会.html";
			break;
		case "売上原価照会":
			strHtmlName = "P03_売上原価照会.html";
			break;
		case "受注売上明細照会":
			strHtmlName = "S06_受注売上明細照会.html";
			break;
		case "受注実績・予算・見込照会":
			strHtmlName = "S07_受注実績・予算・見込照会.html";
			break;
		case "受注内訳レポート":
			strHtmlName = "S08_受注内訳レポート.html";
			break;
		case "年齢表":
			strHtmlName = "S09_年齢表.html";
			break;
		case "得意先仕入先照会":
			strHtmlName = "M01_得意先仕入先照会.html";
			break;
		case "得意先明細":
			strHtmlName = "M02_得意先明細.html";
			break;
		case "仕入先明細":
			strHtmlName = "M03_仕入先明細.html";
			break;
		case "パスワード初期化":
			strHtmlName = "X01_パスワード初期化.html";
			break;
//		case "業績管理＆製造原価表　出力画面":
//			strHtmlName = "X10_業績管理＆製造原価表出力画面.html";	//新規
//			break;

	}
	if(confirm(strViewName+"を起動します。よろしいですか？")){
		window.open(strHtmlName,"","width=987,height=634,resizable=yes,scrollbars=yes,location=yes,status=yes");
	}
}

function moveView(strViewName){


	var strHtmlName;

	switch (strViewName){
		case "ログイン":
			strHtmlName = "Z01_ログイン.html";
			break;
		case "パスワード変更":
			strHtmlName = "Z02_パスワード変更.html";
			break;
		case "パスワード初期化":
			strHtmlName = "X01_パスワード初期化.html";
			break;
		case "メニュー":
			strHtmlName = "Z03_メニュー.html"
			break;
	}

	location.href(strHtmlName);

}



function getRadValue(strName) {

	var ele = document.getElementsByName(strName);
	for(i=0; i<ele.length; i++){
		if(ele[i].checked){return ele[i].value;}
	}

}
