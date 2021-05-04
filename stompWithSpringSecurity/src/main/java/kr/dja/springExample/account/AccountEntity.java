package kr.dja.springExample.account;

import lombok.*;
import javax.persistence.*;



/**
 * 사용자 계정 DB
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountEntity
{
	/** 엔티티 식별자 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/** 유저 아이디 */
	@Column(unique=true, nullable=false, length=50)
	private String userId;
	
	/** 비밀번호 */
	@Column(nullable=false)
	@Builder.Default
	private String password = "";
	
	/** 활성화 여부 */
	@Column(nullable=false)
    private boolean isEnabled;
	
	/** 유저 타입 */
    private UserType userType;

}
