package kr.dja.springExample.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountData
{
	/** 식별자 */
	private Long id;
	
	/** 유저 아이디 */
	private String userId;
	
	/** 비밀번호 */
	private String password;
	
	/** 활성화 여부 */
    private boolean isEnabled;
	
	/** 유저 타입 */
    private UserType userType;
}
