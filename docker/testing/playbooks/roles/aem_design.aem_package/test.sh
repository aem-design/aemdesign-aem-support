
# update os
if [[ "$(which pip3)" == "" ]]; then

  if [[ "$(which yum)" == "" ]]; then
    sudo apt-get update
    sudo apt-get install -y python3 python3-pip python3-venv build-essential libssl-dev libffi-dev python3-dev libcurl4-openssl-dev curl
  else
    yum apt-get install -y python3 python3-pip python3-venv libcurl-devel curl
  fi

fi

# setup py env
if [ ! -d "~/environments" ]; then
  cd ~
  mkdir "environments"
  cd environments
  python3 -m venv my_env
  . ~/environments/my_env/bin/activate
  pip3 install --ignore-installed PyYAML
  pip3 install --upgrade pip
  pip3 install -r requirements.txt

fi
. ~/environments/my_env/bin/activate

#run test
molecule --debug test --destroy=never
