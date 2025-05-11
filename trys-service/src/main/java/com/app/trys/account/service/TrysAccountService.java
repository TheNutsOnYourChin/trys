package com.app.trys.account.service;

import com.app.trys.account.dto.TrysAccountDTO;
import com.app.trys.account.dto.query.TrysAccountQueryDTO;
import com.app.trys.account.event.TrysAccountEvents;
import com.app.trys.base.service.RequestService;
import com.app.trys.event.request.listener.OnRequestEventHandler;
import com.app.trys.object.TablePageDTO;

/**
 * @author linjf
 * @since 2023/6/30
 */
public interface TrysAccountService extends RequestService {


    @OnRequestEventHandler(TrysAccountEvents.Query.class)
    TablePageDTO<TrysAccountDTO> doQuery(TrysAccountQueryDTO queryDTO);


    @OnRequestEventHandler(TrysAccountEvents.Save.class)
    void doSave(TrysAccountDTO accountDTO);


    @OnRequestEventHandler(TrysAccountEvents.Delete.class)
    void doDelete(TrysAccountDTO accountDTO);

}
