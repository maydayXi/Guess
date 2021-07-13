# Guess（4 Digits）
使用 Kotlin 製作的 Android 小遊戲

### 玩法

遊戲隨機產生一組不重複的四位數字（[0-9]{4}），玩家輸入四數字進行猜測答案，每輸入一輪，提示會以XAYB 格式呈現，<spna sytle='color: red; font-size: 14.5pt;'>A 代表數字正確且置正確；B 代表數字正確 但 位置不正確</span>。E.G. Answer is 8<span style='color: green; font-size: 13.5pt;'>5</span><span style='color: #fada5e; font-size: 13.5pt;'>9</span>0，User Input is 1579，Output is <span style='color: green; font-size: 14.5pt;'>1A</span><span style='color: #fada5e;font-size: 13.5pt;'>1B</span> ，一直猜中答案，提示為 4A0B 為止

### ScreenShot
<img src="https://i.imgur.com/pN6yIaQ.png?1" title="Main Frame" />&nbsp;&nbsp;&nbsp;<img src="https://i.imgur.com/vKW5yzX.png?1" title="Game Start" />&nbsp;&nbsp;&nbsp;<img src="https://i.imgur.com/qQMREpS.png?1" title="Result"/>
<img src="https://i.imgur.com/qD8by0l.png?1" title="Game Finished"/>&nbsp;&nbsp;&nbsp;<img src="https://i.imgur.com/Kiw0SfG.png?1" title="source: imgur.com" />

### History

- 2021-07-08 v1.0：基本功能完成
- 2021-07-11 v1.5：增加記錄功能，新增 Room 資料庫模組
- 2021-07-12 v1.6：增加單元測試試模組
- 2021-07-12 v1.7：新增刪除記錄功能
- 2021-07-13 v1.7.1：新增重玩單元測試
