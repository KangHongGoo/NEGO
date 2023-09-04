package kr.co.tj.replyInfi;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@DynamicInsert
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "replyInfi")
public class ReplyEntity {
	

	@Column
	private Long qnaid; // 게시물의 고유 id = bid
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long replyid; // 댓글의 고유 id
	
	@ManyToOne(fetch = FetchType.LAZY)   // 다대일 어노테이션 , 자연로딩 fetch
	@JoinColumn(name = "parentReplyid")
	private ReplyEntity parentReplyid; // 부모 댓글의 고유 id 참조하여 무한 댓글
	
	@OneToMany(mappedBy = "parentReplyid", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReplyEntity> replies; // 대댓글 목록
	
	@Column(nullable = false)
	private String username;

	@Column(length = 500, nullable = false)
	private String content;
	
	private Date createDate;
	
	private Date updateDate;
	
	
	
	

}
