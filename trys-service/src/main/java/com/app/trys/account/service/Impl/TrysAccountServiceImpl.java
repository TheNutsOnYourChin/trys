package com.app.trys.account.service.Impl;

import com.app.trys.account.dto.TrysAccountDTO;
import com.app.trys.account.dto.query.TrysAccountQueryDTO;
import com.app.trys.account.service.TrysAccountService;
import com.app.trys.object.TablePageDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linjf
 * @since 2023/6/30
 */
@Service
public class TrysAccountServiceImpl implements TrysAccountService {

	@Override
	public TablePageDTO<TrysAccountDTO> doQuery(TrysAccountQueryDTO queryDTO) {
		// todo example table
		TablePageDTO<TrysAccountDTO> pageResult = new TablePageDTO<>();
		List<TrysAccountDTO> list = new ArrayList<>();
		long totalSize = 100L;
		for (long i = 0L; i < totalSize; i++) {
			TrysAccountDTO dto = new TrysAccountDTO();
			dto.setId(i);
			dto.setAccount("My account" + i);
			dto.setPw("Pw" + i);
			dto.setName("Account name" + i);
			dto.setWebSite("website" + i);
			list.add(dto);
		}
		pageResult.setList(list);
		pageResult.setTotalPage(totalSize);
		pageResult.setSize(50L);
		pageResult.setCurrent(1L);
		pageResult.setTotal(totalSize / 50L + 1);
		return pageResult;
	}

	@Override
	public void doSave(TrysAccountDTO trysAccountDTO) {
		// todo save
	}

	@Override
	public void doDelete(TrysAccountDTO accountDTO) {
		// todo delete
	}
}
