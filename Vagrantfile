# Primary configuration for bringing up a Kafka/Zookeeper instance
#
# Created by Chris Baumbauer <cab@cabnetworks.net>

Vagrant.configure("2") do |config|
  config.vm.define "node1", primary: true do |node1|
    node1.vm.box = "ubuntu/bionic64"
    node1.vm.hostname = "node1"
    node1.vm.network "private_network", ip: "192.168.66.2"

    node1.vm.provision "shell", inline: <<-SHELL
      if [ ! -d /home/vagrant/zookeeper-data ]; then
        mkdir /home/vagrant/zookeeper-data
        echo "1" > /home/vagrant/zookeeper-data/myid
        chown -R vagrant:vagrant /home/vagrant/zookeeper-data
      fi

      cp /vagrant/config/node1-kafka.properties /home/vagrant/kafka_2.12-2.5.0/config/kafka.properties
      systemctl start zookeeper.service
      systemctl start kafka.service
    SHELL
  end

  config.vm.define "node2" do |node2|
    node2.vm.box = "ubuntu/bionic64"
    node2.vm.hostname = "node2"
    node2.vm.network "private_network", ip: "192.168.66.3"

    node2.vm.provision "shell", inline: <<-SHELL
      if [ ! -d /home/vagrant/zookeeper-data ]; then
        mkdir /home/vagrant/zookeeper-data
        echo "2" > /home/vagrant/zookeeper-data/myid
        chown -R vagrant:vagrant /home/vagrant/zookeeper-data
      fi

      cp /vagrant/config/node2-kafka.properties /home/vagrant/kafka_2.12-2.5.0/config/kafka.properties
      systemctl start zookeeper.service
      systemctl start kafka.service
    SHELL
  end

  config.vm.define "node3" do |node3|
    node3.vm.box = "ubuntu/bionic64"
    node3.vm.hostname = "node3"
    node3.vm.network "private_network", ip: "192.168.66.4"

    node3.vm.provision "shell", inline: <<-SHELL
      if [ ! -d /home/vagrant/zookeeper-data ]; then
        mkdir /home/vagrant/zookeeper-data
        echo "3" > /home/vagrant/zookeeper-data/myid
        chown -R vagrant:vagrant /home/vagrant/zookeeper-data
      fi

      cp /vagrant/config/node3-kafka.properties /home/vagrant/kafka_2.12-2.5.0/config/kafka.properties
      systemctl start zookeeper.service
      systemctl start kafka.service
    SHELL
  end

  # Global provisioner to perform install and generic configuration
  config.vm.provision "shell", inline: <<-SHELL
    apt-get update
    # Should perform an upgrade on the box, however kernel changes will require
    # the nodes to be stopped/restarted
    #apt-get dist-upgrade -y
    apt-get install -y openjdk-8-jdk-headless
    #apt-get autoremove -y

    if [ ! -d /vagrant/tmp ]; then
      mkdir /vagrant/tmp
    fi

    cd /vagrant/tmp
    if [ ! -f /vagrant/tmp/kafka_2.12-2.5.0.tgz ]; then
      wget http://apache.cs.utah.edu/kafka/2.5.0/kafka_2.12-2.5.0.tgz
    fi

    if [ ! -f /vagrant/tmp/zookeeper-3.4.14.tar.gz ]; then
      wget http://apache.cs.utah.edu/zookeeper/zookeeper-3.4.14/zookeeper-3.4.14.tar.gz
    fi

    cd /home/vagrant
    if [ ! -d /home/vagrant/kafka_2.12-2.5.0 ]; then
      tar -xzvf /vagrant/tmp/kafka_2.12-2.5.0.tgz
      chown -R vagrant:vagrant /home/vagrant/kafka_2.12-2.5.0
    fi

    if [ ! -d /home/vagrant/zookeeper-3.4.14 ]; then
      tar -xzvf /vagrant/tmp/zookeeper-3.4.14.tar.gz
      chown -R vagrant:vagrant /home/vagrant/zookeeper-3.4.14
    fi

    cp /vagrant/config/zoo.properties /home/vagrant/zookeeper-3.4.14/conf/zoo.cfg
    cp /vagrant/config/zookeeper.service /lib/systemd/system
    cp /vagrant/config/kafka.service /lib/systemd/system
    systemctl enable zookeeper.service
    systemctl enable kafka.service
    systemctl daemon-reload
  SHELL

  config.vm.provider "vmware_desktop" do |vm|
    vm.vmx["memsize"] = "2048"
    vm.vmx["numvcpus"] = "2"
  end

  config.vm.provider "virtualbox" do |vb|
    vb.cpus = 2
    vb.memory = 2048
  end
end
