<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value='${commonParameters.defaultKeyword}'/>">
<meta name="description" content="<c:out value='${commonParameters.defaultDescription}'/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>登録情報の変更｜<c:out value='${commonParameters.panaReSmail}'/></title>

<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/parts.css" rel="stylesheet">

<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.ah-placeholder.js"></script>

<script type="text/javascript">
	<!--
	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}

	function zipToAddress() {
		if ($("#zip").css('color') ==  'silver' || $("#zip").val() == null || $("#zip").val().length == 0) {
			alert("郵便番号を入力してください。");
			$("#zip").focus();
			return;
		}
		$("#address").focus();
		$.getJSON("../zipToAddress/", {zip:$("#zip").val()}, function(dataObj){
			if (dataObj.zipMst[0] != "0") {
				$("#prefCd").val("");
				$("#address").val("");
				alert(dataObj.zipMst[1]);
				$("#zip").focus();
				return;
			}
			$("#prefCd").val(dataObj.zipMst[1]);
			$("#address").val(dataObj.zipMst[2]);
		});
	}
	// -->
</script>
<!--[if lte IE 9]><script src="/common/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->
</head>

<body>
<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />
<div id="ptop"></div>
<!--#include virtual="/common/ssi/simple-header-D.html"-->

<div id="contents">
	<div id="contentsInner">
		<div class="section01">
			<div id="topicPath">
				<div id="topicInner">
					<ul class="clearfix">
							<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>">トップ</a></li>
							<li><a href="<c:out value="${pageContext.request.contextPath}/mypage/"/>">マイページ</a></li>
							<li class="current">登録情報の変更</li>
					</ul>
				</div>
			</div>
			<div class="headingBlockA01 clearfix">
				<h1 class="ttl">登録情報の変更</h1>
			</div><!-- /.headingBlockA01 -->
			<nav class="stepChartNav step01">
				<ul>
					<li class="current"><span>入力</span></li>
						<li><span>確認</span></li>
					<li><span>完了</span></li>
				</ul>
			</nav>
			<p><c:import url="/WEB-INF/front/default_jsp/include/validationerrors.jsh" /></p>
			<form action="./confirm" method="post" name="inputForm">
			<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
			<input type="hidden" name="insDate" value="<c:out value="${inputForm.insDate}"/>">
			<input type="hidden" name="updDate" value="<c:out value="${inputForm.updDate}"/>">
			<input type="hidden" name="promoCd" value="<c:out value="${inputForm.promoCd}"/>">
			<input type="hidden" name="refCd" value="<c:out value="${inputForm.refCd}"/>">
			<input type="hidden" name="projectFlg" value="front">
			<div class="itemBlockA01 spMb00">
				<div class="headingBlockD01 clearfix">
						<h2 class="ttl">ログイン情報</h2>
				</div><!-- /.headingBlockD01 -->
				<div class="columnInner">
						<p class="mb10"><span class="mustIcon">必須</span> 項目は必ずご入力ください。</p>
						<table class="tableBlockA01">
							<tr>
								<th><label for="mail01">メールアドレス<span class="mustIcon">必須</span></label></th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="mypage.input.email" />
									</c:import>
									<div><input type="text" name="email" value="<c:out value="${inputForm.email}"/>" class="inputType04 <c:if test="${dm3hasError}">error</c:if>" id="mail01"
												maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.email" defaultValue="255"/>">
									<span class="spDisBlock mt05">（半角英数）</span></div>
								</td>
							</tr>
							<tr>
									<th class="pb00"><p class="mb10"><label for="newPassword01">パスワード</label></p>
									<p class="f10">※変更する場合は、新しいパスワードを入力し直してください。</p></th>
									<td class="pb00">
										<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
										<c:param name="targetLabel" value="mypage.input.password" />
										</c:import>
										<div><input type="password" class="inputType04 <c:if test="${dm3hasError}">error</c:if>" id="newPassword01" id="password" name="password" value="<c:out value="${inputForm.password}"/>" minlength="8" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.password" defaultValue="16"/>"><span class="spDisBlock mt05">（半角英数）</span></div>
										<div class="mt10">※8文字以上16文字以下で入力してください。<br>
										※英大文字と英小文字と数字を必ず混在させてください。</div>
									</td>
							</tr>
							<tr>
									<th></th>
									<td class="pb00">
										<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
										<c:param name="targetLabel" value="mypage.input.rePassword" />
										</c:import>
										<input type="password" class="inputType04 <c:if test="${dm3hasError}">error</c:if>" id="newPassword02" name="passwordChk" value="<c:out value="${inputForm.passwordChk}"/>" minlength="8" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.rePassword" defaultValue="16"/>">
										<div class="mt10">※確認のため、もう一度パスワードを入力してください。</div>
									</td>
							</tr>
						</table>
					</div><!-- /.columnInner -->
				</div><!-- /.itemBlockA01 -->
			<div class="itemBlockA01 spMb00">
			<div class="headingBlockD01 clearfix">
				<h2 class="ttl">お客様情報</h2>
			</div><!-- /.headingBlockD01 -->
				<div class="columnInner">
					<table class="tableBlockA01">
						<tr>
							<th><label for="name01">お名前<span class="mustIcon">必須</span></label></th>
							<td>
								<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
								<c:param name="targetLabel" value="mypage.input.memberLname" />
								</c:import>
								<c:set var="lnameHasError" value="${dm3hasError}" scope="request"/>
								<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
								<c:param name="targetLabel" value="mypage.input.memberFname" />
								</c:import>
								<c:set var="fnameHasError" value="${dm3hasError}" scope="request"/>
								<div>姓 <input type="text" name="memberLname" value="<c:out value="${inputForm.memberLname}"/>" id="name01" class="inputType05 <c:if test="${lnameHasError}">error</c:if>" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.memberLname" defaultValue="30"/>">
									名 <input type="text" name="memberFname" value="<c:out value="${inputForm.memberFname}"/>" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.memberFname" defaultValue="30"/>" class="inputType05 <c:if test="${fnameHasError}">error</c:if>" ></div>
								</div>
							</td>
						</tr>
						<tr>
							<th><label for="name02">お名前（フリガナ）<span class="mustIcon">必須</span></label></th>
							<td>
								<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
								<c:param name="targetLabel" value="mypage.input.memberLnameKana" />
								</c:import>
								<c:set var="lnameKanaHasError" value="${dm3hasError}" scope="request"/>
								<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
								<c:param name="targetLabel" value="mypage.input.memberFnameKana" />
								</c:import>
								<c:set var="fnameKanaHasError" value="${dm3hasError}" scope="request"/>
								<div>姓 <input type="text" name="memberLnameKana" value="<c:out value="${inputForm.memberLnameKana}"/>"  maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.memberLname" defaultValue="30"/>" class="inputType05 <c:if test="${lnameKanaHasError}">error</c:if>" id="name02" >
								名 <input type="text" name="memberFnameKana" value="<c:out value="${inputForm.memberFnameKana}"/>"  maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.memberFname" defaultValue="30"/>" class="inputType05 <c:if test="${fnameKanaHasError}">error</c:if>" ></div>
							</td>
						</tr>
						<tr>
							<th><label for="post01">郵便番号<span class="mustIcon">必須</span></label></th>
							<td>
								<div>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="member.input.zip" />
									</c:import>
									<input type="text" id="zip" name="zip" class="inputType02 <c:if test="${dm3hasError}">error</c:if>" value="<c:out value="${inputForm.zip}"/>" size="5" maxlength="7">
									<a href="javascript:zipToAddress();" class="addressBtn">住所検索</a>
									<a href="http://www.post.japanpost.jp/zipcode/" target="_blank" class="link02">郵便番号がわからない方</a>
									<div class="mt10">※半角数字、ハイフンなしで入力してください。</div>
								</div>
							</td>
						</tr>
						<tr>
							<th>都道府県<span class="mustIcon">必須</span></th>
							<td>
								<div>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="member.input.prefCd" />
									</c:import>
									<select id="prefCd" name="prefCd">
										<option value="">選択してください</option>
										<c:forEach items="${prefMstList}" var="prefMst">
											<option value="<c:out value="${prefMst.prefCd}"/>"<c:if test="${prefMst.prefCd == inputForm.prefCd}"> selected</c:if>><c:out value="${prefMst.prefName}"/></option>
										</c:forEach>
									</select>
								</div>
							</td>
						</tr>
						<tr>
							<th><label for="area01">市区町村番地<span class="mustIcon">必須</span></label></th>
							<td>
								<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
								<c:param name="targetLabel" value="member.input.address" />
								</c:import>
								<div><input type="text" id="address" name="address" class="inputType03 <c:if test="${dm3hasError}">error</c:if>" value="<c:out value="${inputForm.address}"/>" size="20" maxlength="50"></div>
							</td>
						</tr>
						<tr>
							<th><label for="building01">建物名</label></th>
							<td>
								<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
								<c:param name="targetLabel" value="member.input.addressOther" />
								</c:import>
								<div><input type="text" id="building01" name="addressOther" class="inputType03 <c:if test="${dm3hasError}">error</c:if>" value="<c:out value="${inputForm.addressOther}"/>" size="42" maxlength="30"></div>
							</td>
						</tr>
						<tr>
							<th><label for="tel01">電話番号<span class="mustIcon">必須</span></label></th>
							<td>
								<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
								<c:param name="targetLabel" value="member.input.tel" />
								</c:import>
								<div><input type="text" id="tel01" name="tel" class="inputType04 <c:if test="${dm3hasError}">error</c:if>" value="<c:out value="${inputForm.tel}"/>" size="14" maxlength="11" ></div>
								<div class="mt05">※半角数字、ハイフンなしで市外局番から入力してください。<br>
									※携帯電話・PHSの番号でもご登録いただけます。</div>
							</td>
						</tr>
						<tr>
							<th>居住状態</th>
							<td>
								<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
								<c:param name="targetLabel" value="member.input.residentFlg" />
								</c:import>
								<dm3lookup:lookupForEach lookupName="residentFlg">
									<label><input type="radio" name="residentFlg" value="<c:out value="${key}"/>" <c:if test="${inputForm.residentFlg == key}">checked</c:if>><c:out value="${value}"/></label>&nbsp;&nbsp;
								</dm3lookup:lookupForEach>
							</td>
						</tr>
					</table>
				</div><!-- /.columnInner -->
			</div><!-- /.itemBlockA01 -->
			<div class="itemBlockA01 spMb00">
					<div class="headingBlockD01 clearfix">
							<h2 class="ttl">物件希望地域</h2>
					</div><!-- /.headingBlockD01 -->
				<div class="columnInner">
						<div class="contentsInner01">
							<p>売却予定物件、または購入希望物件の所在地を選択してください。</p>
						</div>
						<table class="tableBlockA01">
								<tr>
									<th>都道府県<span class="mustIcon">必須</span></th>
									<td>
										<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
										<c:param name="targetLabel" value="member.input.hopePrefCd" />
										</c:import>
										<div>
											<select name="hopePrefCd">
												<option value="">選択してください</option>
												<c:forEach items="${prefMstList}" var="prefMst">
													<option value="<c:out value="${prefMst.prefCd}"/>"<c:if test="${prefMst.prefCd == inputForm.hopePrefCd}"> selected</c:if>><c:out value="${prefMst.prefName}"/></option>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<th><label for="area02">市区町村</label></th>
									<td>
										<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
										<c:param name="targetLabel" value="member.input.hopeAddress" />
										</c:import>
										<div><input type="text" id="area02" class="inputType03 <c:if test="${dm3hasError}">error</c:if>" name="hopeAddress" value="<c:out value="${inputForm.hopeAddress}"/>" maxlength="50"></div>
									</td>
								</tr>
							</table>
							</div><!-- /.columnInner -->
					</div><!-- /.itemBlockA01 -->
				<div class="itemBlockA01 spMb00">
			<div class="headingBlockD01 clearfix">
				<h2 class="ttl">メルマガ配信設定</h2>
			</div><!-- /.headingBlockD01 -->
			<div class="columnInner">
				<table class="tableBlockA01">
					<tr>
						<th>メルマガ配信<span class="mustIcon">必須</span></th>
						<td>
							<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
							<c:param name="targetLabel" value="member.input.mailSendFlg" />
							</c:import>
							<dm3lookup:lookupForEach lookupName="mailSendFlg">
								<label><input type="radio" name="mailSendFlg" value="<c:out value="${key}"/>" <c:if test="${inputForm.mailSendFlg == key}">checked</c:if>><c:out value="${value}"/></label>&nbsp;&nbsp;
							</dm3lookup:lookupForEach>
						</td>
					</tr>
				</table>
				</div><!-- /.columnInner -->
				</div><!-- /.itemBlockA01 -->
				<div class="contentsInner01">
					<p class="center mb15 spPb10 spMt10">
					<button type="submit" onClick="linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/mypage/member/change/confirm/', '');" name="confirmBtn" id="confirmBtn" class="primaryBtn01">入力内容を確認する</button>
					</p>
				</div>
			</form>
		</div>
	</div>
</div>
<!--#include virtual="/common/ssi/simple-footer-S.html"-->

</body>
</html>
