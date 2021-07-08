# Guess（4 Digits）
使用 Kotlin 製作的 Android 小遊戲

### 玩法

遊戲隨機產生一組不重複的四位數字（[0-9]{4}），玩家輸入四數字進行猜測答案，每輸入一輪，提示會以XAYB 格式呈現，<spna sytle='color: red; font-size: 14.5pt;'>A 代表數字正確且置正確；B 代表數字正確 但 位置不正確</span>E.G. Answer is 8<span style='color: green; font-size: 13.5pt;'>5</span><span style='color: #fada5e; font-size: 13.5pt;'>9</span>0，User Input is 1579，Output is <span style='color: green; font-size: 14.5pt;'>1A</span><span style='color: #fada5e;font-size: 13.5pt;'>1B</span> ，一直猜中答案，提示為 4A0B 為止

### 操作方法

遊戲開始畫面
<img src="https://i.imgur.com/vKW5yzX.png" title="Game Start" style="width: 35%;" />

輸入數字
<img src="https://i.imgur.com/GowNIS0.png" title="User Input"  style="width: 35%;"/>

按下 OK 按鈕，輸出提示結果
<img src="https://i.imgur.com/cWzqttW.png" title="Hint" style='width: 35%; display: inline-block;'/><img src="https://i.imgur.com/qQMREpS.png" title="Result" style="width: 35%; display: inline-block;" />

直到猜到答案，提示輸出 4A0B，遊戲結束
<img src="https://i.imgur.com/qD8by0l.png" title="Game Finished" style='width: 35%;' />

按下右下「<img src="https://i.imgur.com/Zpn013d.png" title="Replay" style='width: 5%;'/>」按鈕，重新開始遊戲

<img src="https://i.imgur.com/IUO5F10.png" title="Replay Dialog" style="width: 35%; margin-top: 8px;"/><img src="https://i.imgur.com/Shq1f3F.png" title="Replay" style='width: 35%;' style="margin-top: 8px;"/>

### HIstory

- 2021-07-08 V1.0：基本功能完成
