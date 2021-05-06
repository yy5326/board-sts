package com.board.service;

import java.util.List;

import com.board.domain.BoardDTO;

public interface BoardService {
	// 게시글 등록
	public boolean registerBoard(BoardDTO params);
	
	// 게시글 목록 (+ 게시글 총 개수)
	public List<BoardDTO> getBoardList();
}