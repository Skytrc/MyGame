package com.fung.server.gameserver.content.domain.time;

/**
 * TODO 暂时不使用TriggerTaskExecutor
 * 定时调度任务
 * @author skytrc@163.com
 * @date 2020/7/7 21:01
 */
public interface TriggerFuture {

    TriggerFuture FINISH_FUTURE = new TriggerFuture() {
        @Override
        public boolean isFinish() {
            return true;
        }

        @Override
        public void cancel() {

        }
    };

    /**
     * 是否完成任务
     * @return 是否完成任务
     */
    boolean isFinish();

    /**
     * 取消任务
     */
    void cancel();
}
