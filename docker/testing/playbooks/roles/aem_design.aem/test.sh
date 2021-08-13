
yum install -y python3
pip3 install --ignore-installed PyYAML
pip3 install --upgrade pip
pip3 install testinfra molecule docker molecule-docker yamllint flake8 ansible
molecule --debug test
