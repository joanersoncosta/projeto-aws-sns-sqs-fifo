aws sqs get-queue-attributes --queue-url https://sqs.us-east-1.amazonaws.com/588738590129/sqs-queue --attribute-names ApproximateNumberOfMessages 

aws sqs receive-message --queue-url https://sqs.us-east-1.amazonaws.com/588738590129/sqs-queue

//Mandar msg
 aws sns publish --topic-arn arn:aws:sns:us-east-1:588738590129:sns-teste-topic-fifo.fifo --message "Olá, esta é uma mensagem para o tópico FIFO!" --message-group-id "group1"
 
  --message-deduplication-id "abc123"