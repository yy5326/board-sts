package com.board.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Override
	public boolean registerBoard(BoardDTO params) {
		  int queryResult = 0;
		  // idx(인덱스)가 없다는 건, 아직 생성되지 않는 게시물이기 때문에,
		  // 게시물을 생성하는 쿼리 실행
		  if(params.getIdx() == null) {
		  	queryResult = boardMapper.insertBoard(params);
		  } 
		  // idx(인덱스)가 있으면, 게시물이 존재하므로, 게시물을 수정하는 쿼리 실행
		  else {
		  	queryResult = boardMapper.updateBoard(params);
		  }

		  // 쿼리가 정상적으로 실행되면 1을 반환함.
		  // 정상적으로 실행되면 true, 아니면 false
		  return (queryResult == 1) ? true : false;
	}

	@Override
	public List<BoardDTO> getBoardList() {

		  List<BoardDTO> boardList = Collections.emptyList();

		  // 게시글 총 개수를 가져와 저장
		  int boardTotalCount = boardMapper.selectBoardTotalCount();

		  // 게시글이 있을 경우에만, 게시글 목록을 가져오는 Mapper 실행
		  if(boardTotalCount > 0) {
		  boardList = boardMapper.selectBoardList();
		  }

		  return boardList;
	}
	
	// 게시글 조회
	@Override
	public BoardDTO getBoardDetail(Long idx) {

		return boardMapper.selectBoardDetail(idx);
	}
}