package aircraftwar.application;

import java.util.HashMap;
import java.util.Map;

public final class I18n {

    private static Language language = Language.ZH;
    private static final Map<String, String> ZH = new HashMap<>();
    private static final Map<String, String> EN = new HashMap<>();

    static {
        put("title", "飞机大战", "Aircraft War");
        put("language", "语言 / Language", "Language / 语言");
        put("chooseMode", "请选择难度", "Choose Difficulty");
        put("easy", "简单模式", "Easy");
        put("normal", "普通模式", "Normal");
        put("hard", "困难模式", "Hard");
        put("score", "分数", "Score");
        put("hp", "生命值", "HP");
        put("gameOver", "游戏结束", "Game Over");
        put("savePrompt", "游戏结束！请输入您的姓名：\n得分：%d", "Game over! Enter your name:\nScore: %d");
        put("saveTitle", "保存记录", "Save Score");
        put("leaderboard", "排行榜", "Leaderboard");
        put("rank", "名次", "Rank");
        put("player", "玩家名", "Player");
        put("time", "记录时间", "Time");
        put("delete", "删除记录", "Delete");
        put("playAgain", "再来一局", "Play Again");
        put("backMenu", "返回菜单", "Back to Menu");
        put("exit", "退出", "Exit");
        put("selectRecord", "请先选择要删除的记录！", "Please select a record first.");
        put("confirmDelete", "确定要删除这条记录吗？", "Delete this record?");
        put("confirmDeleteTitle", "确认删除", "Confirm Delete");
        put("deleted", "删除成功！", "Deleted.");
        put("notice", "提示", "Notice");
        put("ok", "确定", "OK");
        put("cancel", "取消", "Cancel");
        put("yes", "是", "Yes");
        put("no", "否", "No");
    }

    private I18n() {
    }

    public static void setLanguage(Language selectedLanguage) {
        language = selectedLanguage;
    }

    public static Language getLanguage() {
        return language;
    }

    public static String text(String key) {
        Map<String, String> bundle = language == Language.ZH ? ZH : EN;
        return bundle.getOrDefault(key, key);
    }

    public static String format(String key, Object... args) {
        return String.format(text(key), args);
    }

    private static void put(String key, String zh, String en) {
        ZH.put(key, zh);
        EN.put(key, en);
    }
}

