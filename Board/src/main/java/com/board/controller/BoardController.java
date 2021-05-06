package com.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.domain.BoardDTO;
import com.board.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 게시글 등록 GET
	@GetMapping(value = "/board/write.do")
	public String openBoardWrite(@RequestParam(value = "idx", required = false) Long idx, Model model) {
	  // idx가 없으면 게시물이 생성되지 않은 상태이기 때문에,
	  // 새로운 BoardDTO(즉, null 상태)를 모델에 담아 리턴
	  if(idx == null) {
	  	model.addAttribute("board", new BoardDTO());
	  } 
	  // idx가 있으면 게시물이 생성되어 있는 상태이기 때문에,
	  // 게시물을 조회하고, 그 정보를 모델에 담아 리턴
	  else {
	  	BoardDTO board = boardService.getBoardDetail(idx);

	  // 아무 정보도 조회되지 않았다면, list로 리다이렉트
	  	if(board == null) {
	    	return "redirect:/board/list.do";
	    }

	    model.addAttribute("board", board);
	  }

	  return "board/write";
	}
	
	// 게시글 작성 POST
	@PostMapping(value = "/board/register.do")
	public String registerBoard(final BoardDTO params) {
		try {
			boolean isRegistered = boardService.registerBoard(params);
			if (isRegistered == false) {
				// TODO => 게시글 등록에 실패하였다는 메시지를 전달
			}
		} catch (DataAccessException e) {
			// TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지를 전달

		} catch (Exception e) {
			// TODO => 시스템에 문제가 발생하였다는 메시지를 전달
		}

		return "redirect:/board/list.do";
	}
	
	// 게시글 목록
	@GetMapping(value = "/board/list.do")
	  public String openBoardList(Model model) {

	  List<BoardDTO> boardList = boardService.getBoardList();
	  model.addAttribute("boardList", boardList);

	  return "board/list";
	}
	
	// 게시글 조회
	@GetMapping(value = "/board/view.do")
	public String openBoardDetail(@RequestParam(value = "idx", required = false) Long idx, Model model) {

	  // 올바르지 않은 접근 시
	  if(idx == null) {
	    // => 올바르지 않은 접근이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
	    return "redirect:/board/list.do";
	  }

	  BoardDTO board = boardService.getBoardDetail(idx);

	  // 없는 게시글이거나, 이미 삭제된 게시글일 경우
	  if(board == null || "Y".equals(board.getDeleteYn())) {
	    // => 삭제된 게시글이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
	    return "redirect:/board/list.do";
	  }

	  model.addAttribute("board", board);

	  return "board/view";
	}
	
}