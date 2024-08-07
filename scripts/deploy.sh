
#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=freelec-springboot3-webservice #해당 위치에 properties에 작성한 프로젝트명과 동일하게 작성합니다.

echo "> Build 파일 복사"
cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 현재 구동 중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -f $PROJECT_NAME)

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다"
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR_NAME: $JAR_NAME"
echo "> $JAR_NAME 에 실행권한 추가"
chmod +x $JAR_NAME

# 설정 파일들에 대한 권한 추가
sudo chown ec2-user:ec2-user /home/ec2-user/app/application-oauth.yml
sudo chown ec2-user:ec2-user /home/ec2-user/app/application-real-db.yml
chmod 644 /home/ec2-user/app/application-oauth.yml
chmod 644 /home/ec2-user/app/application-real-db.yml

echo "> $JAR_NAME 실행"
nohup java -jar \
  -Dspring.config.location=classpath:/application.yml,classpath:/application-real.yml,/home/ec2-user/app/application-oauth.yml,/home/ec2-user/app/application-real-db.yml \
  -Dspring.profile.active=real \
  $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &