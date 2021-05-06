package com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.board.domain.BoardDTO;

@Mapper
public interface BoardMapper {
	// 게시글 생성
	public int insertBoard(BoardDTO params);	
	
	// 게시글 목록
	public List<BoardDTO> selectBoardList();

	// 게시글 총 갯수
	public int selectBoardTotalCount();
	
	// 게시글 조회
	public BoardDTO selectBoardDetail(Long idx);
	
	// 게시글 수정
	public int updateBoard(BoardDTO params);
}