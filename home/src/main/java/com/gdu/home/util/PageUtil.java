package com.gdu.home.util;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter // 세터는 넣으면 임의로 값을 바꿀수도 있어서 안넣는다
@Component
public class PageUtil {

		private int page;   		// 현재 페이지(파라미터로 받아온다, 해당 페이지가 없으면 디폴트값으로 있으면 해당페이지)
		private int totalRecord;    // 전체 레코드 개수(DB에서 구해온다)
		private int recordPerPage;  // 한 페이지 표시할 레코드 개수(파라미터로 받아온다)
		private int begin;			// 한 페이지에 표시할 레코드의 시작번호(계산한다)
		
		private int pagePerBlock = 5;	// 한 블록에 표시할 페이지의 개수(임의로 정한다, 유일하게 세팅된 값이다.) 
		private int totalPage;			// 전체 페이지 개수(계산한다)
		private int beginPage;			// 한 블록에 표시할 페이지의 시작 번호(계산한다)
		private int endPage;			// 한 블록에 표시할 페이지의 종료 번호(계산한다)
		
		public void setPageutil(int page, int totalRecord, int recordPerPage) {  // set형식의 저장하는 형식의 메소드이다.
			
			// page, totalRecord, recordPerPage 저장
			this.page = page;
			this.totalRecord = totalRecord;
			this.recordPerPage = recordPerPage;
			
			  begin = (page - 1) * recordPerPage;
			  
			  // totalPage 계산 
			  totalPage = totalRecord / recordPerPage; 
			  if(totalRecord % recordPerPage != 0) {
				  totalPage++;
			  }
			
			  // beginPage, endPage 계산
			  /*
			   totalPage=6, pagePerBlock=4인 상황 
			   block	beginPage	endPage
			   1(1~4)	1			4
			   2(5~6)	5			6	
			   */
			  
			   beginPage = ((page - 1) / pagePerBlock) * pagePerBlock + 1;
			   endPage = beginPage + pagePerBlock - 1;
			   if(endPage > totalPage) {
				   endPage = totalPage;
			   }
			  
			  
		}

		public String getPagination(String path) {
			// path = request.getContextPath() + "/employees/pagination.do?order=" + order 이렇게 값이 넘어온다,
			// 이미 파라미터 값이 들어가있는지 확인한다(?때문에)
			
			// path에 ?가 포함되어 있으면 이미 파라미터가 포함된 경로이므로 &를 붙여서 page파라미터를 추가한다, 주소창에서 page파라미터를 추가해준다
			
			if(path.contains("?")) { // 물음표가 포함되어 있냐? 라는 자바식 검사 메소드
				path += "&";
			}else {
				path += "?";
			}
			StringBuilder sb = new StringBuilder();
			
			
			
				
			sb.append("<div class=\"pagination\">");
			
			// 이전 블록 : 1블록은 이전 블록이 없고, 나머지 블록은 이전 블록이 있다.
			if(beginPage == 1) {
				sb.append("<span class=\"hidden\">◀</span>");
			}else {
				sb.append("<a class=\"link\" href=\"" + path + "page=" + (beginPage - 1) + "\">◀</a>");
			}
			
			// 페이지번호 : 현재 페이지는 링크가 없다.
			for(int p = beginPage; p <= endPage; p++) {
				if(p == page) {
					sb.append("<span class=\"strong\">" + p + "</span>");
				}else {
					sb.append("<a class=\"link\" href=\"" + path + "page=" + p + "\">" + p + "</a>");
				}
			}
			
			// 다음 블록 : 마지막 블록은 다음 블록이 없고, 나머지 블록은 다음 블록이 있다.
			if(endPage == totalPage) {
				sb.append("<span class=\"hidden\">▶</span>");
			}else {
				sb.append("<a class=\"link\" href=\"" + path + "page=" + (endPage + 1) + "\">▶</a>");
			}
			
			sb.append("</div>");
			
			return sb.toString();
			
			
		}
}