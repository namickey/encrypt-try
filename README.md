# encrypt-try

# AES

## 鍵長・IV長

| | 鍵長 | IV長 |
|---|---|---|
| GCM | 32バイト(256ビット) | 12バイト(96ビット) |
| PKCS5Padding(CBC) | 32バイト(256ビット) | 16バイト(128ビット) |
| NoPadding(CBC) | 32バイト(256ビット) | 16バイト(128ビット) |

- 鍵長はモード(GCM/CBC)に関係なく共通。JDK21の`KeyGenerator.getInstance("AES")`のデフォルト鍵長(256ビット)を使用している。
- GCMのIV長(12バイト)は推奨される最適値、CBCのIV長(16バイト)はAESのブロックサイズに合わせる必要がある必須値という違いがある。

## 平文長と暗号文長(IVは含まない暗号文本体のみのバイト数。かっこ内はBase64エンコード後の文字数)

| 平文長 | CBC/NoPadding | CBC/PKCS5Padding | GCM/NoPadding |
|---|---|---|---|
| 1byte | エラー(16の倍数でない) | 16byte(24文字) | 17byte(24文字) |
| 2byte | エラー(16の倍数でない) | 16byte(24文字) | 18byte(24文字) |
| 3byte | エラー(16の倍数でない) | 16byte(24文字) | 19byte(28文字) |
| 15byte | エラー(16の倍数でない) | 16byte(24文字) | 31byte(44文字) |
| 16byte | 16byte(24文字) | 32byte(44文字) | 32byte(44文字) |
| 31byte | エラー(16の倍数でない) | 32byte(44文字) | 47byte(64文字) |
| 32byte | 32byte(44文字) | 48byte(64文字) | 48byte(64文字) |
| 33byte | エラー(16の倍数でない) | 48byte(64文字) | 49byte(68文字) |
| 47byte | エラー(16の倍数でない) | 48byte(64文字) | 63byte(84文字) |
| 48byte | 48byte(64文字) | 64byte(88文字) | 64byte(88文字) |

- **CBC/NoPadding**: 平文がそのまま暗号文になる。16の倍数でない場合は`Input length not multiple of 16 bytes`エラーになる。
- **CBC/PKCS5Padding**: 16バイト単位に切り上げてパディングする。平文長が16の倍数のとき(16byte, 32byte, 48byte)でも、必ず1ブロック分(16byte)の余分なパディングが追加される。
- **GCM/NoPadding**: パディングは発生せず、平文長に認証タグ(16byte)を加えた長さになる。

