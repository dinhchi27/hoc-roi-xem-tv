#!/usr/bin/env python3
"""Generate update metadata after building; publish APK before pushing this file."""
from pathlib import Path
import hashlib, json, xml.etree.ElementTree as ET
root = Path(__file__).resolve().parents[1]
manifest = ET.parse(root / 'app/src/main/AndroidManifest.xml').getroot()
version = manifest.attrib['{http://schemas.android.com/apk/res/android}versionName']
name = f'Hoc-Roi-Xem-TV-v{version}.apk'
apk = root / 'out' / name
payload = {
    'tag_name': f'v{version}', 'draft': False, 'prerelease': False,
    'body': 'Cải thiện kiểm tra cập nhật: nguồn thông tin tĩnh, API dự phòng và thông báo lỗi chi tiết.',
    'assets': [{'name': name, 'size': apk.stat().st_size,
                'digest': 'sha256:' + hashlib.sha256(apk.read_bytes()).hexdigest(),
                'browser_download_url': f'https://github.com/dinhchi27/hoc-roi-xem-tv/releases/download/v{version}/{name}'}]
}
(root / 'updates/latest.json').write_text(json.dumps(payload, ensure_ascii=False, indent=2) + '\n')
print('Generated updates/latest.json for ' + version)
