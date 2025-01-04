from openai import OpenAI

with open("apikey") as f:
    apikey = f.read()
client = OpenAI(
    api_key=apikey
)

topics_used = []


def choose_topic():
    completion = client.chat.completions.create(
        model="gpt-4o-mini",
        store=False,
        messages=[
            {"role": "user",
             "content": get_topic_content()}
        ]
    )
    result = completion.choices[0].message.content.replace("'", "", 2).split(":")
    topics_used.append(result[0])
    return result


def check_used_topic_amount():
    if len(topics_used) > 50:
        topics_used.clear()


def guess_word(guessed_word, question):
    completion = client.chat.completions.create(
        model="gpt-4o-mini",
        store=False,
        messages=[
            {"role": "system",
             "content": get_guess_word_content(guessed_word)},
            {"role": "user",
             "content": question}
        ]
    )
    return completion.choices[0].message.content


def get_guess_word_content(guessed_word):
    content = (
        f"We play guessing game. The guessed word or phrase is {guessed_word}. I don't know the word or phrase and I "
        f"should guess "
        f"it. So I'll send you a question trying to guess and you "
        f"should answer. If my question is related to guessed "
        f"word or phrase, answer something in agreement like Yes or familiar. If my question is not related to guessed "
        f"word or phrase answer something like No or familiar. If I guessed the "
        f"word or phrase then answer 'done' and only in this format, note that if I wrote guessed word or phrase in "
        f"any format in "
        f"the question, that means that I guessed this. If you "
        f"dont know what to answer then write that you don't know.")
    return content


def get_topic_content():
    content = (f"Write topic and word or phrase related to the topic, topic and word or phrase you choose yourself "
               f"but they should be "
               f"interesting and not so simple. I know that if I ask you later about the new topic you will "
               f"suggest me the same again, so try to write me not default topics. Send only the "
               f"topic and the word formating like topic:word and nothing else, for example 'sports:pull ups' or 'web "
               f"programming:css'. "
               f"You have already suggested me topics {topics_used}, so don't choose them.")
    return content
