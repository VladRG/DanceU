:-use_module(library(sockets)).
:-use_module( library(lists)).

start_process:-
    format('Initializing Prolog Database\n',[]),	
    flush_output,
    prolog_flag(argv, [PortSocket|_]), 
    atom_chars(PortSocket,LCifre),
    number_chars(Port,LCifre),
    socket_client_open(localhost: Port, Stream, [type(text)]),
    process_text(Stream,0).
				
				
process_text(Stream, C):-
    read(Stream, ReadCommand),
    process_command(Stream, ReadCommand, C).

process_command(Stream, get_dance(Dance_Type), C):-
    dance(Dance_Type, Description, Name),
    format(Stream, '~s#~s\n', [Description, Name]),
    flush_output(Stream),
    C1 is C + 1,
    process_text(Stream, C1).

process_command(Stream, get_dances, C):-
    findall(Title, dance(Dance, Desc, Title), Dances),
    format(Stream, 'dances::~s#~s#~s#~s#~s#~s#~s#~s#~s#~s#~s#~s#~s#~s#~s#~s#~s\n', Dances),
    flush_output(Stream),
    C1 is C + 1,
    process_text(Stream, C1).

dance(salsa, 'descriere Salsa', 'Salsa').
dance(tango, 'descriere Tango', 'Tango').
dance(street_dance, 'descriere Street Dance', 'Street Dance').
dance(swing, 'descriere Swing', 'Swing').
dance(classic_balet, 'descriere Balet Clasic', 'Balet Clasic').
dance(paso_doble, 'descriere Paso Doble', 'Paso Doble').
dance(hip_hop, 'descriere Hip Hop', 'Hip Hop').
dance(twist, 'descriere Hip Hop', 'Twist').
dance(modern_dance, 'descriere Hip Hop', 'Dans Modern').
dance(contemporary_dance, 'descriere Hip Hop', 'Dans Contemporan').
dance(zumba, 'descriere Hip Hop', 'Zumba').
dance(classic_waltz, 'descriere Hip Hop', 'Vals Clasic').
dance(breakdance, 'descriere Hip Hop', 'Breakdance').
dance(bachata, 'descriere Hip Hop', 'Bachata').
dance(aerobic, 'descriere Hip Hop', 'Aerobic').
dance(sportive_dance, 'descriere Hip Hop', 'Dans Sportiv').
dance(viennese_waltz, 'descriere Hip Hop', 'Vals Vienez').
    
personality_type(introvert, 'Introvert').
personality_type(extrovert, 'Extrovert').
personality_type(ambivert, 'Ambivert').
process_command(Stream, get_personality_types, C):-
    findall(Title, personality_type(Type, Title), Types),
    format(Stream, 'personality_types::~s#~s#~s\n', Types),
    flush_output(Stream),
    C1 is C + 1,
    process_text(Stream, C1).

health_status(unhealthy, 'Bolnav').
health_status(medium, 'Normal').
health_status(healthy, 'Sanatos').
process_command(Stream, get_health_statuses, C):-
    findall(Title, health_status(Type, Title), Types),
    format(Stream, 'health_statuses::~s#~s#~s\n', Types),
    flush_output(Stream),
    C1 is C + 1,
    process_text(Stream, C1).

budget(low, 'Mic').
budget(medium, 'Mediu').
budget(high, 'Mare').
process_command(Stream, get_budgets, C):-
    findall(Title, budget(Type, Title), Types),
    format(Stream, 'budgets::~s#~s#~s\n', Types),
    flush_output(Stream),
    C1 is C + 1,
    process_text(Stream, C1).

availability(low, 'Mic').
availability(medium, 'Mediu').
availability(high, 'Mare').
process_command(Stream, get_availabilities, C):-
    findall(Title, availability(Type, Title), Types),
    format(Stream, 'availabilities::~s#~s#~s\n', Types),
    flush_output(Stream),
    C1 is C + 1,
    process_text(Stream, C1).

age(child, 'Copil').
age(adolencent, 'Adolescent').
age(adult, 'Adult').
age(senior, 'Senior').
process_command(Stream, get_ages, C):-
    findall(Title, age(Type, Title), Types),
    format(Stream, 'ages::~s#~s#~s#~s\n', Types),
    flush_output(Stream),
    C1 is C + 1,
    process_text(Stream, C1).


rythm(slow, 'Lent').
rythm(fast, 'Vioi').
process_command(Stream, get_rythms, C):-
    findall(Title, rythm(Type, Title), Types),
    format(Stream, 'rythms::~s#~s\n', Types),
    flush_output(Stream),
    C1 is C + 1,
    process_text(Stream, C1).

purpose(wedding, 'Nunta').
purpose(ball, 'Bal').
purpose(recreational, 'Recreational').
purpose(competition, 'Competitional').
process_command(Stream, get_purposes, C):-
    findall(Title, purpose(Type, Title), Types),
    format(Stream, 'purposes::~s#~s#~s#~s\n', Types),
    flush_output(Stream),
    C1 is C + 1,
    process_text(Stream, C1).

passinate_about_dance(yes).
passinate_about_dance(no).

experience(yes).
experience(no).

optimist(yes).
optimist(no).

